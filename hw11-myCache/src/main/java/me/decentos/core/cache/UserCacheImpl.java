package me.decentos.core.cache;

import me.decentos.cache.Listener;
import me.decentos.cache.MyCache;
import me.decentos.cache.MyCacheImpl;
import me.decentos.core.model.User;

import java.util.Optional;

public class UserCacheImpl implements UserCache {
    private final MyCache<String, User> cache = new MyCacheImpl<>();

    @Override
    public void add(User user) {
        cache.put(getKey(user.getId()), user);
    }

    @Override
    public Optional<User> get(long id) {
        return Optional.ofNullable(cache.get(getKey(id)));
    }

    @Override
    public void remove(User user) {
        cache.remove(getKey(user.getId()));
    }

    @Override
    public void addListener(Listener<String, User> listener) {
        cache.addListener(listener);
    }

    @Override
    public void removeListener(Listener<String, User> listener) {
        cache.removeListener(listener);
    }

    private String getKey(long id) {
        return String.format("User_%d", id);
    }
}
