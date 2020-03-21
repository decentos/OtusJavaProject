package me.decentos.jdbc.dao;

import me.decentos.core.dao.AccountDao;
import me.decentos.core.dao.UserDaoException;
import me.decentos.core.model.Account;
import me.decentos.core.sessionmanager.SessionManager;
import me.decentos.jdbc.DbExecutorException;
import me.decentos.jdbc.sessionmanager.SessionManagerJdbc;
import me.decentos.mapper.jdbc.JdbcMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.Optional;

public class AccountDaoJdbc implements AccountDao {
    private static Logger logger = LoggerFactory.getLogger(AccountDaoJdbc.class);

    private final SessionManagerJdbc sessionManager;
    private final JdbcMapper<Account> mapper;

    public AccountDaoJdbc(SessionManagerJdbc sessionManager, JdbcMapper<Account> mapper) {
        this.sessionManager = sessionManager;
        this.mapper = mapper;
    }

    @Override
    public Optional<Account> findByNo(long no) {
        try {
            return mapper.load(getConnection(), no, Account.class);
        } catch (DbExecutorException e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public long saveAccount(Account account) {
        try {
            mapper.createOrUpdate(getConnection(), account);
            return account.getNo();
        } catch (DbExecutorException e) {
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
