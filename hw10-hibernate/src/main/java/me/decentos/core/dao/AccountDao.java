package me.decentos.core.dao;

import me.decentos.core.model.Account;
import me.decentos.core.sessionmanager.SessionManager;

import java.util.Optional;

public interface AccountDao {
    Optional<Account> findByNo(long no);

    long saveAccount(Account account);

    SessionManager getSessionManager();
}
