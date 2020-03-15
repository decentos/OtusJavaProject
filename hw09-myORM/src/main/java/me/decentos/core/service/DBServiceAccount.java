package me.decentos.core.service;

import me.decentos.core.model.Account;

import java.util.Optional;

public interface DBServiceAccount {

    long saveAccount(Account account);

    Optional<Account> getAccount(long no);

}
