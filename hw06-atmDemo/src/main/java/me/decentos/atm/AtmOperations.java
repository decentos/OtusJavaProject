package me.decentos.atm;

import me.decentos.AtmLogic;
import me.decentos.banknotes.Banknotes;

public interface AtmOperations {
    void withdraw(AtmLogic atmLogic, int balanceOfAccount, int withdrawAmount);
    void deposit(AtmLogic atmLogic, int balanceOfAccount, Banknotes... banknotes);
    void getBalanceOfAtm();
}
