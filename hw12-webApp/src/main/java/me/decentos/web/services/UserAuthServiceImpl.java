package me.decentos.web.services;

import me.decentos.core.service.ServiceUser;

public class UserAuthServiceImpl implements UserAuthService {

    private final ServiceUser userService;

    public UserAuthServiceImpl(ServiceUser userService) {
        this.userService = userService;
    }

    @Override
    public boolean authenticate(String login, String password) {
        return userService.getUser(login)
                .map(user -> user.getPassword().equals(password) && user.getAdmin())
                .orElse(false);
    }

}
