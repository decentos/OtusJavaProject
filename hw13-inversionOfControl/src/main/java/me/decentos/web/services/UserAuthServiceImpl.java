package me.decentos.web.services;

import me.decentos.core.service.UserService;

public class UserAuthServiceImpl implements UserAuthService {

    private final UserService userService;

    public UserAuthServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean authenticate(String login, String password) {
        return userService.getUser(login)
                .map(user -> user.getPassword().equals(password) && user.getAdmin())
                .orElse(false);
    }

}
