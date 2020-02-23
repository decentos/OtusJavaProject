package me.decentos;

import me.decentos.banknotes.Banknotes;

public interface AtmLogic {
    void startUseAtm(int balanceOfAccount);
    int getBalanceOfAccount();
    void setBalanceOfAccount(int balanceOfAccount);
    void withdraw(int withdrawAmount);
    void deposit(Banknotes... banknotes);
    void getBalanceOfAtmAndAvailableBanknotes(String password);
    int getBalanceOfAtm(String password);
    void resetAtmToDefaultState();
}
