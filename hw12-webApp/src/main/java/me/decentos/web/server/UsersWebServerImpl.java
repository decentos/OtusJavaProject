package me.decentos.web.server;

import com.google.gson.Gson;
import me.decentos.core.service.UserService;
import me.decentos.web.helpers.FileSystemHelper;
import me.decentos.web.services.TemplateProcessor;
import me.decentos.web.services.UserAuthService;
import me.decentos.web.servlet.*;
import org.eclipse.jetty.security.ConstraintMapping;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.LoginService;
import org.eclipse.jetty.security.SecurityHandler;
import org.eclipse.jetty.security.authentication.BasicAuthenticator;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.security.Constraint;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class UsersWebServerImpl implements UsersWebServer {
    private static final String START_PAGE_NAME = "index.html";
    private static final String COMMON_RESOURCES_DIR = "static";
    private static final String ROLE_NAME_USER = "user";
    private static final String ROLE_NAME_ADMIN = "admin";
    private static final String CONSTRAINT_NAME = "auth";

    private final int port;
    private final SecurityType securityType;
    private final UserAuthService userAuthServiceForFilterBasedSecurity;
    private final LoginService loginServiceForBasicSecurity;
    private final UserService userService;
    private final Gson gson;
    private final TemplateProcessor templateProcessor;
    private final Server server;

    public UsersWebServerImpl(int port, SecurityType securityType,
                              UserAuthService userAuthServiceForFilterBasedSecurity,
                              LoginService loginServiceForBasicSecurity,
                              UserService userService,
                              Gson gson,
                              TemplateProcessor templateProcessor) {
        this.port = port;
        this.securityType = securityType;
        this.userAuthServiceForFilterBasedSecurity = userAuthServiceForFilterBasedSecurity;
        this.loginServiceForBasicSecurity = loginServiceForBasicSecurity;
        this.userService = userService;
        this.gson = gson;
        this.templateProcessor = templateProcessor;
        server = initContext();
    }

    @Override
    public void start() throws Exception {
        server.start();
    }

    @Override
    public void join() throws Exception {
        server.join();
    }

    @Override
    public void stop() throws Exception {
        server.stop();
    }

    private Server initContext() {
        HandlerList handlers = new HandlerList();

        ResourceHandler resourceHandler = createResourceHandler();
        handlers.addHandler(resourceHandler);

        ServletContextHandler servletContextHandler = createServletContextHandler();
        handlers.addHandler(applySecurity(servletContextHandler));

        Server srv = new Server(port);
        srv.setHandler(handlers);
        return srv;
    }

    private ResourceHandler createResourceHandler() {
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(false);
        resourceHandler.setWelcomeFiles(new String[]{START_PAGE_NAME});
        resourceHandler.setResourceBase(FileSystemHelper.localFileNameOrResourceNameToFullPath(COMMON_RESOURCES_DIR));
        return resourceHandler;
    }

    private ServletContextHandler createServletContextHandler() {
        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContextHandler.addServlet(new ServletHolder(new LogoutServlet()), "/logout");
        servletContextHandler.addServlet(new ServletHolder(new AdminServlet(templateProcessor)), "/admin");
        servletContextHandler.addServlet(new ServletHolder(new UsersApiServlet(userService)), "/api/user/*");
        return servletContextHandler;
    }

    private Handler applySecurity(ServletContextHandler servletContextHandler) {
        if (securityType == SecurityType.NONE) {
            return servletContextHandler;
        } else if (securityType == SecurityType.FILTER_BASED) {
            applyFilterBasedSecurity(servletContextHandler, "/admin", "/api/user/*");
            return servletContextHandler;
        } else if (securityType == SecurityType.BASIC) {
            return createBasicAuthSecurityHandler(servletContextHandler, "/admin", "/api/user/*");
        } else {
            throw new InvalidSecurityTypeException(securityType);
        }
    }

    private ServletContextHandler applyFilterBasedSecurity(ServletContextHandler servletContextHandler, String... paths) {
        servletContextHandler.addServlet(new ServletHolder(new LoginServlet(templateProcessor, userAuthServiceForFilterBasedSecurity)), "/login");
        AuthorizationFilter authorizationFilter = new AuthorizationFilter();
        IntStream.range(0, paths.length)
                .forEachOrdered(i -> servletContextHandler.addFilter(new FilterHolder(authorizationFilter), paths[i], null));
        return servletContextHandler;
    }

    private SecurityHandler createBasicAuthSecurityHandler(ServletContextHandler context, String... paths) {
        Constraint constraint = new Constraint();
        constraint.setName(CONSTRAINT_NAME);
        constraint.setAuthenticate(true);
        constraint.setRoles(new String[]{ROLE_NAME_USER, ROLE_NAME_ADMIN});

        List<ConstraintMapping> constraintMappings = new ArrayList<>();
        IntStream.range(0, paths.length).forEachOrdered(i -> {
            ConstraintMapping mapping = new ConstraintMapping();
            mapping.setPathSpec(paths[i]);
            mapping.setConstraint(constraint);
            constraintMappings.add(mapping);
        });

        ConstraintSecurityHandler security = new ConstraintSecurityHandler();
        security.setAuthenticator(new BasicAuthenticator());

        security.setLoginService(loginServiceForBasicSecurity);
        security.setConstraintMappings(constraintMappings);
        security.setHandler(new HandlerList(context));

        return security;
    }
}
