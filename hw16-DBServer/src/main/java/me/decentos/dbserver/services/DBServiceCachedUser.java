package me.decentos.dbserver.services;

import me.decentos.dbserver.cache.MyCacheImpl;
import me.decentos.dbserver.entity.User;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Component
public class DBServiceCachedUser {
    private final MyCacheImpl<String, User> entityCache;

    private final DBServiceUser dbServiceUser;

    @PostConstruct
    private void init() {
        saveUser(new User("Test1", "Test1", 11));
        saveUser(new User("Test2", "Test2", 22));
        saveUser(new User("Test3", "Test3", 33));
        saveUser(new User("Test4", "Test4", 44));
        saveUser(new User("Test5", "Test5", 55));
    }


    public DBServiceCachedUser(DBServiceUser serviceUser, MyCacheImpl<String, User> entityCache) {
        this.dbServiceUser = serviceUser;
        this.entityCache = entityCache;
    }


    public long saveUser(User user) {
        long id = dbServiceUser.saveUser(user);
        entityCache.put(String.valueOf(user.getId()), user);
        return id;
    }

    public Optional<User> getUser(long id) {
        return entityCache.get(String.valueOf(id)).or(() -> dbServiceUser.getUser(id).map(user -> {
            entityCache.put(String.valueOf(id), user);
            return user;
        }));
    }

    public List<User> getUsersList() {
        return dbServiceUser.getUsersList();
    }

}
