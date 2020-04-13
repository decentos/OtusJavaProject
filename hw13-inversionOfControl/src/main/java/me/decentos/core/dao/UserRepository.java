package me.decentos.core.dao;

import me.decentos.core.model.User;
import me.decentos.core.sessionmanager.SessionManager;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<User> getAll();

    Optional<User> findByLogin(String login);

    Optional<User> findById(long id);

    long saveUser(User user);

    SessionManager getSessionManager();
}
