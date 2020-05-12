package me.decentos.dbserver.services;

import me.decentos.dbserver.entity.User;

import java.util.List;
import java.util.Optional;

public interface DBServiceUser {

    long saveUser(User user);

    Optional<User> getUser(long id);

    List<User> getUsersList();


}
