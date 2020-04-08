package me.decentos.web.servlet;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import me.decentos.core.model.User;
import me.decentos.core.service.ServiceException;
import me.decentos.core.service.UserService;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class UsersApiServlet extends HttpServlet {
    private static final int ID_PATH_PARAM_POSITION = 1;
    private static final String PARAM_LOGIN = "login";
    private static final String PARAM_PASSWORD = "password";
    private static final String PARAM_NAME = "name";
    private static final String IS_ADMIN = "isAdmin";

    private final UserService userService;

    public UsersApiServlet(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String login = request.getParameter(PARAM_LOGIN);
        String password = request.getParameter(PARAM_PASSWORD);
        String name = request.getParameter(PARAM_NAME);
        Boolean isAdmin = request.getParameter(IS_ADMIN) != null && request.getParameter(IS_ADMIN).equals("true");

        User newUser = new User(name);
        newUser.setLogin(login);
        newUser.setPassword(password);
        newUser.setAdmin(isAdmin);

        try {
            userService.saveUser(newUser);
            response.setStatus(HttpServletResponse.SC_CREATED);
        } catch (ServiceException e) {
            response.setContentType("text/html;charset=UTF-8");
            response.getOutputStream().print(e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long userId = extractIdFromRequest(request);
        String data;
        if (userId != -1) {
            // exact user
            data = userToJsonObject(userService.getUser(userId).orElse(null)).toString();
        } else {
            // all users
            data = usersToJsonArray().toString();
        }
        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream out = response.getOutputStream();
        out.print(data);
    }

    private long extractIdFromRequest(HttpServletRequest request) {
        String[] path = request.getPathInfo().split("/");
        String id = (path.length > 1) ? path[ID_PATH_PARAM_POSITION] : String.valueOf(-1);
        return Long.parseLong(id);
    }

    private JsonObject userToJsonObject(User user) {
        JsonObject userObject = new JsonObject();
        if (user != null) {
            userObject.add("id", new JsonPrimitive(user.getId()));
            userObject.add("name", new JsonPrimitive(user.getName()));
            userObject.add("login", new JsonPrimitive(user.getLogin()));
            userObject.add("password", new JsonPrimitive("******"));
        }
        return userObject;
    }

    private JsonArray usersToJsonArray() {
        JsonArray jsonArray = new JsonArray();
        userService.getAll().forEach(user -> {
            jsonArray.add(userToJsonObject(user));
        });
        return jsonArray;
    }
}
