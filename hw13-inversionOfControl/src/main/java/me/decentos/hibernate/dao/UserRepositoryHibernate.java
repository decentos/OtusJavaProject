package me.decentos.hibernate.dao;


import me.decentos.core.model.User;
import me.decentos.core.repository.UserRepository;
import me.decentos.core.repository.UserRepositoryException;
import me.decentos.core.sessionmanager.SessionManager;
import me.decentos.hibernate.sessionmanager.DatabaseSessionHibernate;
import me.decentos.hibernate.sessionmanager.SessionManagerHibernate;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepositoryHibernate implements UserRepository {
    private static Logger logger = LoggerFactory.getLogger(UserRepositoryHibernate.class);

    private final SessionManagerHibernate sessionManager;

    public UserRepositoryHibernate(SessionManagerHibernate sessionManager) {
        this.sessionManager = sessionManager;
    }


    @Override
    public List<User> getAll() {
        try {
            Session hibernateSession = sessionManager.getCurrentSession().getHibernateSession();
            Query<User> userQuery = hibernateSession.createQuery("select e from User e", User.class);
            return userQuery.getResultList();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return new ArrayList<>();
    }

    @Override
    public Optional<User> findByLogin(String login) {
        try {
            Session hibernateSession = sessionManager.getCurrentSession().getHibernateSession();
            Query<User> userQuery = hibernateSession.createQuery("select e from User e where e.login = ?1", User.class);
            userQuery.setParameter(1, login);
            return Optional.ofNullable(userQuery.getSingleResult());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findById(long id) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            return Optional.ofNullable(currentSession.getHibernateSession().find(User.class, id));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }


    @Override
    public long saveUser(User user) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            Session hibernateSession = currentSession.getHibernateSession();
            if (user.getId() > 0) {
                hibernateSession.merge(user);
            } else {
                hibernateSession.persist(user);
            }
            return user.getId();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserRepositoryException(e);
        }
    }

    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }
}
