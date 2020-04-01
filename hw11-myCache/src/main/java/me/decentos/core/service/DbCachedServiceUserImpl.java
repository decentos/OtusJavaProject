package me.decentos.core.service;

import me.decentos.core.cache.UserCache;
import me.decentos.core.dao.UserDao;
import me.decentos.core.model.User;
import me.decentos.core.sessionmanager.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class DbCachedServiceUserImpl implements DBServiceUser {
    private static Logger logger = LoggerFactory.getLogger(DbCachedServiceUserImpl.class);

    private final UserDao userDao;
    private final UserCache cache;

    public DbCachedServiceUserImpl(UserDao userDao, UserCache cache) {
        this.userDao = userDao;
        this.cache = cache;
    }

    @Override
    public long saveUser(User user) {
        try (SessionManager sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                long userId = userDao.saveUser(user);
                sessionManager.commitSession();
                cache.add(user);

                logger.info("saved user: {}", userId);
                return userId;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }

    @Override
    public Optional<User> getUser(long id) {
        try (SessionManager sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<User> userOptional = cache.get(id).or(() -> {
                    var user = userDao.findById(id);
                    user.ifPresent(cache::add);
                    return user;
                });

                logger.info("loaded user: {}", userOptional.orElse(null));
                return userOptional;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return Optional.empty();
        }
    }

}
