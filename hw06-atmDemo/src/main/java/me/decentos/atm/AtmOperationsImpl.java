package me.decentos.atm;

import me.decentos.AtmLogic;
import me.decentos.banknotes.Banknotes;
import me.decentos.banknotes.BanknotesStore;
import me.decentos.banknotes.BanknotesStoreImpl;

public class AtmOperationsImpl implements AtmOperations {
    private BanknotesStore banknotesStore = new BanknotesStoreImpl();

    @Override
    public void withdraw(AtmLogic atmLogic, int balanceOfAccount, int withdrawAmount) {
        if (withdrawAmount > balanceOfAccount) {
            throw new RuntimeException("Not enough money for withdraw! Your balance: " + atmLogic.getBalanceOfAccount() + "₽");
        }
        else if (withdrawAmount > AtmBalanceImpl.getBalanceOfAtm()) {
            throw new RuntimeException("Not enough money for withdraw! ATM balance: " + AtmBalanceImpl.getBalanceOfAtm() + "₽");
        }
        else if (withdrawAmount % 10 != 0) {
            throw new RuntimeException("You can only withdraw a multiple of 10₽");
        }

        balanceOfAccount -= withdrawAmount;
        atmLogic.setBalanceOfAccount(balanceOfAccount);
        banknotesStore.receiveBanknotesForWithdrawal(withdrawAmount);
        System.out.println("Balance after withdraw: " + atmLogic.getBalanceOfAccount()  + "₽");
    }

    @Override
    public void deposit(AtmLogic atmLogic, int balanceOfAccount, Banknotes... banknotes) {
        int depositAmount = 0;
        for (Banknotes b: banknotes) {
            depositAmount += b.getBanknote();
            banknotesStore.plusBanknotesCount(b);
        }
        balanceOfAccount += depositAmount;
        atmLogic.setBalanceOfAccount(balanceOfAccount);
        System.out.println("Your deposit: " + depositAmount + "₽. Balance after deposit: " + atmLogic.getBalanceOfAccount() + "₽");
    }
}