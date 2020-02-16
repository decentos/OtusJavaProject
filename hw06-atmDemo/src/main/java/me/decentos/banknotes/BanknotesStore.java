package me.decentos.banknotes;

public interface BanknotesStore {
    void receiveBanknotesForWithdrawal(int withdrawAmount);
    void plusBanknotesCount(Banknotes banknote);
    void minusBanknotesCount(Banknotes banknote);
    void getAvailableBanknotesForWithdraw();
    void fillAtmByBanknotes();
}
