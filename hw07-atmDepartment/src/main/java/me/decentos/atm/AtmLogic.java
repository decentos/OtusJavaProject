package me.decentos.atm;

import me.decentos.banknotes.Banknotes;
import me.decentos.banknotes.BanknotesStore;
import me.decentos.banknotes.BanknotesStoreImpl;

public class AtmLogic {
    private int balanceOfAccount;
    private AtmOperationsImpl atmOperations = new AtmOperationsImpl();
    private BanknotesStore banknotesStore = new BanknotesStoreImpl();

    public AtmLogic() {
        banknotesStore.fillAtmByBanknotes();
    }

    public void startUseAtm(int balanceOfAccount) {
        this.balanceOfAccount = balanceOfAccount;
    }

    public int getBalanceOfAccount() {
        return balanceOfAccount;
    }

    public void setBalanceOfAccount(int balanceOfAccount) {
        this.balanceOfAccount = balanceOfAccount;
    }

    public void withdraw(int withdrawAmount) {
        atmOperations.withdraw(this, this.balanceOfAccount, withdrawAmount);
    }

    public void deposit(Banknotes... banknotes) {
        atmOperations.deposit(this, this.balanceOfAccount, banknotes);
    }

    public void getBalanceOfAtmAndAvailableBanknotes(String password) {
        if (password.equals("passForAdmin")) {
            atmOperations.getBalanceOfAtmAndAvailableBanknotes();
        }
        else {
            throw new RuntimeException("You entered the wrong password.");
        }
    }

    public int getBalanceOfAtm(String password) {
        if (password.equals("passForAdmin")) {
            return atmOperations.getBalanceOfAtm();
        }
        else {
            throw new RuntimeException("You entered the wrong password.");
        }
    }
}