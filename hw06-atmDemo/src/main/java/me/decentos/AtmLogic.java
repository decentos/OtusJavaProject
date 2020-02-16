package me.decentos;

import me.decentos.atm.AtmBalance;
import me.decentos.atm.AtmBalanceImpl;
import me.decentos.atm.AtmOperations;
import me.decentos.atm.AtmOperationsImpl;
import me.decentos.banknotes.Banknotes;

public class AtmLogic {
    private int balanceOfAccount;
    private AtmOperations atmOperations = new AtmOperationsImpl();
    private AtmBalance atmBalance = new AtmBalanceImpl();

    public AtmLogic() {
        this.balanceOfAccount = 0;
    }

    public AtmLogic(int balanceOfAccount) {
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
            atmBalance.getBalanceOfAtmAndAvailableBanknotes();
        }
        else {
            throw new RuntimeException("You entered the wrong password.");
        }
    }
}