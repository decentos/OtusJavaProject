package me.decentos.banknotes;

import java.util.Map;

public interface BanknotesStore {
    void receiveBanknotesForWithdrawal(int withdrawAmount);
    void plusBanknotesCount(Banknotes banknote);
    void minusBanknotesCount(Banknotes banknote);
    void getAvailableBanknotesForWithdraw();
    void fillAtmByBanknotes(int fillCount);
    Map<Banknotes, Integer> getBanknotes();
}