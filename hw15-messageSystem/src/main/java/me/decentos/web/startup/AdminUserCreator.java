package me.decentos.web.startup;

import me.decentos.core.model.User;
import me.decentos.core.service.UserService;

public class AdminUserCreator {

    private final UserService userService;

    public AdminUserCreator(UserService userService) {
        this.userService = userService;
    }

    public void createDefaultAdminUser() {
        User admin = new User("Admin");
        admin.setLogin("admin");
        admin.setPassword("123");
        admin.setAdmin(true);

        userService.saveUser(admin);
    }
}
