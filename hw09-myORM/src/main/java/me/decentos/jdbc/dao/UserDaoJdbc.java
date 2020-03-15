package me.decentos.jdbc.dao;


import me.decentos.core.dao.UserDao;
import me.decentos.core.dao.UserDaoException;
import me.decentos.core.model.User;
import me.decentos.core.sessionmanager.SessionManager;
import me.decentos.jdbc.DbExecutor;
import me.decentos.jdbc.DbExecutorException;
import me.decentos.jdbc.sessionmanager.SessionManagerJdbc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class UserDaoJdbc implements UserDao {
  private static Logger logger = LoggerFactory.getLogger(UserDaoJdbc.class);

  private final SessionManagerJdbc sessionManager;

  public UserDaoJdbc(SessionManagerJdbc sessionManager) {
    this.sessionManager = sessionManager;
  }

  @Override
  public Optional<User> findById(long id) {
    try {
      DbExecutor<User> dbExecutor = getExecutor();
      return dbExecutor.load(id, User.class);
    } catch (DbExecutorException e) {
      logger.error(e.getMessage(), e);
    }
    return Optional.empty();
  }

  @Override
  public long saveUser(User user) {
    try {
      DbExecutor<User> dbExecutor = getExecutor();
      dbExecutor.createOrUpdate(user);
      return user.getId();
    } catch (DbExecutorException e) {
      logger.error(e.getMessage(), e);
      throw new UserDaoException(e);
    }
  }

  @Override
  public SessionManager getSessionManager() {
    return sessionManager;
  }

  private DbExecutor<User> getExecutor() {
    return new DbExecutor<>(sessionManager.getCurrentSession().getConnection(), User.class);
  }
}
