package me.decentos;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.decentos.core.dao.UserDao;
import me.decentos.core.model.AddressDataSet;
import me.decentos.core.model.PhoneDataSet;
import me.decentos.core.model.User;
import me.decentos.core.service.UserService;
import me.decentos.core.service.UserServiceImpl;
import me.decentos.hibernate.HibernateUtils;
import me.decentos.hibernate.dao.UserDaoHibernate;
import me.decentos.hibernate.sessionmanager.SessionManagerHibernate;
import me.decentos.web.server.UsersWebServer;
import me.decentos.web.server.UsersWebServerImpl;
import me.decentos.web.services.*;
import org.eclipse.jetty.security.LoginService;
import org.hibernate.SessionFactory;

import static me.decentos.web.server.SecurityType.FILTER_BASED;

public class Main {
    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";
    private static final String HIBERNATE_CFG_XML_FILE_RESOURCE = "hibernate.cfg.xml";

    public static void main(String[] args) throws Exception {
        // hibernate
        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory(HIBERNATE_CFG_XML_FILE_RESOURCE,
                User.class, AddressDataSet.class, PhoneDataSet.class);
        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);

        // DAO and business layer services
        UserDao userDao = new UserDaoHibernate(sessionManager);
        UserService userService = new UserServiceImpl(userDao);

        // Web services
        UserAuthService userAuthServiceForFilterBasedSecurity = new UserAuthServiceImpl(userService);
        LoginService loginServiceForBasicSecurity = new InMemoryLoginServiceImpl(userService);

        // Other
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);
        createAdminUser(userService);

        UsersWebServer usersWebServer = new UsersWebServerImpl(WEB_SERVER_PORT,
                FILTER_BASED,
                userAuthServiceForFilterBasedSecurity,
                loginServiceForBasicSecurity,
                userService,
                gson,
                templateProcessor);

        usersWebServer.start();
        usersWebServer.join();
    }

    private static void createAdminUser(UserService userService) {
        User admin = new User("Admin");
        admin.setLogin("admin");
        admin.setPassword("admin");
        admin.setAdmin(true);

        userService.saveUser(admin);
    }
}
