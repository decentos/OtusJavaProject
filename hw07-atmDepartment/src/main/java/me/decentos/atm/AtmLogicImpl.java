package me.decentos.atm;

import me.decentos.banknotes.Banknotes;

public class AtmLogicImpl implements AtmLogic {
    private int balanceOfAccount;
    private AtmOperations atmOperations = new AtmOperationsImpl();

    @Override
    public void startUseAtm(int balanceOfAccount) {
        this.balanceOfAccount = balanceOfAccount;
    }

    @Override
    public int getBalanceOfAccount() {
        return balanceOfAccount;
    }

    @Override
    public void setBalanceOfAccount(int balanceOfAccount) {
        this.balanceOfAccount = balanceOfAccount;
    }

    @Override
    public void withdraw(int withdrawAmount) {
        atmOperations.withdraw(this, this.balanceOfAccount, withdrawAmount);
    }

    @Override
    public void deposit(Banknotes... banknotes) {
        atmOperations.deposit(this, this.balanceOfAccount, banknotes);
    }

    @Override
    public void getBalanceOfAtmAndAvailableBanknotes(String password) {
        if (password.equals("passForAdmin")) {
            atmOperations.getBalanceOfAtmAndAvailableBanknotes();
        }
        else {
            throw new RuntimeException("You entered the wrong password.");
        }
    }

    @Override
    public int getBalanceOfAtm(String password) {
        if (password.equals("passForAdmin")) {
            return atmOperations.getBalanceOfAtm();
        }
        else {
            throw new RuntimeException("You entered the wrong password.");
        }
    }

    @Override
    public void resetAtmToDefaultState() {
        atmOperations.resetAtmToDefaultState();
    }
}