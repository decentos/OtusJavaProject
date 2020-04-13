package me.decentos.web.services;

import me.decentos.core.dao.UserRepository;
import me.decentos.core.sessionmanager.SessionManager;
import org.springframework.stereotype.Service;

@Service
public class UserAuthServiceImpl implements UserAuthService {

    private final UserRepository userRepository;

    public UserAuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean authenticate(String login, String password) {
        try (SessionManager sessionManager = userRepository.getSessionManager()) {
            sessionManager.beginSession();
            return userRepository.findByLogin(login)
                    .map(user -> user.getPassword().equals(password) && user.getAdmin())
                    .orElse(false);
        }
    }
}
