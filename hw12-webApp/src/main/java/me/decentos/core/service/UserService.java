package me.decentos.core.service;

import me.decentos.core.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    long saveUser(User user);

    Optional<User> getUser(long id);

    Optional<User> getUser(String login);

    List<User> getAll();

}
