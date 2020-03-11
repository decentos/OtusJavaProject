package me.decentos.jdbc.dao;


import me.decentos.core.dao.UserDao;
import me.decentos.core.dao.UserDaoException;
import me.decentos.core.model.User;
import me.decentos.core.sessionmanager.SessionManager;
import me.decentos.jdbc.DbExecutor;
import me.decentos.jdbc.sessionmanager.SessionManagerJdbc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Optional;

public class UserDaoJdbc implements UserDao {
  private static Logger logger = LoggerFactory.getLogger(UserDaoJdbc.class);

  private final SessionManagerJdbc sessionManager;
  private final DbExecutor<User> dbExecutor;

  public UserDaoJdbc(SessionManagerJdbc sessionManager, DbExecutor<User> dbExecutor) {
    this.sessionManager = sessionManager;
    this.dbExecutor = dbExecutor;
  }


  @Override
  public Optional<User> findById(long id) {
    try {
      return dbExecutor.selectRecord(getConnection(), "select id, name from user where id  = ?", id, resultSet -> {
        try {
          if (resultSet.next()) {
            return new User(resultSet.getLong("id"), resultSet.getString("name"));
          }
        } catch (SQLException e) {
          logger.error(e.getMessage(), e);
        }
        return null;
      });
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
    return Optional.empty();
  }


  @Override
  public long saveUser(User user) {
    try {
      return dbExecutor.insertRecord(getConnection(), "insert into user(name) values (?)", Collections.singletonList(user.getName()));
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      throw new UserDaoException(e);
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
