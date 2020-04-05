package me.decentos.web.services;

import me.decentos.core.model.User;
import me.decentos.core.service.UserService;
import org.eclipse.jetty.security.AbstractLoginService;
import org.eclipse.jetty.util.security.Password;

import java.util.Optional;

public class InMemoryLoginServiceImpl extends AbstractLoginService {

    private final UserService userService;

    public InMemoryLoginServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected String[] loadRoleInfo(UserPrincipal userPrincipal) {
        return new String[] {"user"};
    }

    @Override
    protected UserPrincipal loadUserInfo(String login) {
        System.out.println(String.format("InMemoryLoginService#loadUserInfo(%s)", login));
        Optional<User> user = userService.getUser(login);
        return user.map(u -> new UserPrincipal(u.getLogin(), new Password(u.getPassword()))).orElse(null);
    }
}
