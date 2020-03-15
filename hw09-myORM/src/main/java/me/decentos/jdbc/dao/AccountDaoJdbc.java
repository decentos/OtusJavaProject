package me.decentos.jdbc.dao;

import me.decentos.core.dao.AccountDao;
import me.decentos.core.dao.UserDaoException;
import me.decentos.core.model.Account;
import me.decentos.core.sessionmanager.SessionManager;
import me.decentos.jdbc.DbExecutor;
import me.decentos.jdbc.DbExecutorException;
import me.decentos.jdbc.sessionmanager.SessionManagerJdbc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class AccountDaoJdbc implements AccountDao {
    private static Logger logger = LoggerFactory.getLogger(AccountDaoJdbc.class);

    private final SessionManagerJdbc sessionManager;

    public AccountDaoJdbc(SessionManagerJdbc sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public Optional<Account> findByNo(long no) {
        try {
            DbExecutor<Account> dbExecutor = getExecutor();
            return dbExecutor.load(no, Account.class);
        } catch (DbExecutorException e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public long saveAccount(Account account) {
        try {
            DbExecutor<Account> dbExecutor = getExecutor();
            dbExecutor.createOrUpdate(account);
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

    private DbExecutor<Account> getExecutor() {
        return new DbExecutor<>(sessionManager.getCurrentSession().getConnection(), Account.class);
    }
}
