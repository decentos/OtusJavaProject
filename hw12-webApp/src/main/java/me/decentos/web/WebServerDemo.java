package me.decentos.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.decentos.web.dao.InMemoryUserDao;
import me.decentos.web.dao.UserDao;
import me.decentos.web.server.UsersWebServer;
import me.decentos.web.server.UsersWebServerImpl;
import me.decentos.web.services.TemplateProcessor;
import me.decentos.web.services.TemplateProcessorImpl;
import me.decentos.web.services.UserAuthService;
import me.decentos.web.services.UserAuthServiceImpl;

/*
    Полезные для демо ссылки

    // Стартовая страница
    http://localhost:8080

    // Страница пользователей
    http://localhost:8080/users

    // REST сервис
    http://localhost:8080/api/user/3
*/
public class WebServerDemo {
    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";

    public static void main(String[] args) throws Exception {
        UserDao userDao = new InMemoryUserDao();
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);
        UserAuthService authService = new UserAuthServiceImpl(userDao);

        UsersWebServer usersWebServer = new UsersWebServerImpl(WEB_SERVER_PORT,
                authService, userDao, gson, templateProcessor);

        usersWebServer.start();
        usersWebServer.join();
    }
}
