package me.decentos.core.cache;

import me.decentos.cache.Listener;
import me.decentos.core.model.User;

import java.util.Optional;

public interface UserCache {
    String NAME = "userCache";

    void add(User user);

    Optional<User> get(long id);

    void remove(User user);

    void addListener(Listener<String, User> listener);

    void removeListener(Listener<String, User> listener);
}
