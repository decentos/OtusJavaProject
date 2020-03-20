package me.decentos.core.dao;

import me.decentos.core.model.User;
import me.decentos.core.sessionmanager.SessionManager;

import java.util.Optional;

public interface UserDao {
    Optional<User> findById(long id);

    long saveUser(User user);

    SessionManager getSessionManager();
}
