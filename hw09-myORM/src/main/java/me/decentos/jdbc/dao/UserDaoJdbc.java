package me.decentos.jdbc.dao;


import me.decentos.core.dao.DaoException;
import me.decentos.core.dao.UserDao;
import me.decentos.core.model.User;
import me.decentos.core.sessionmanager.SessionManager;
import me.decentos.jdbc.DbExecutorException;
import me.decentos.jdbc.sessionmanager.SessionManagerJdbc;
import me.decentos.mapper.jdbc.JdbcMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.Optional;

public class UserDaoJdbc implements UserDao {
    private static Logger logger = LoggerFactory.getLogger(UserDaoJdbc.class);

    private final SessionManagerJdbc sessionManager;
    private final JdbcMapper<User> mapper;

    public UserDaoJdbc(SessionManagerJdbc sessionManager, JdbcMapper<User> mapper) {
        this.sessionManager = sessionManager;
        this.mapper = mapper;
    }

    @Override
    public Optional<User> findById(long id) {
        try {
            return mapper.load(getConnection(), id, User.class);
        } catch (DbExecutorException e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public long saveUser(User user) {
        try {
            mapper.createOrUpdate(getConnection(), user);
            return user.getId();
        } catch (DbExecutorException e) {
            logger.error(e.getMessage(), e);
            throw new DaoException(e);
        }
    }

    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }

    private Connection getConnection() {
        return sessionManager.getCurrentSession().getConnection();
    }
}
