package me.decentos.atm;

import me.decentos.AtmLogic;
import me.decentos.banknotes.Banknotes;
import me.decentos.banknotes.BanknotesStore;
import me.decentos.banknotes.BanknotesStoreImpl;

public class AtmOperationsImpl implements AtmOperations, AtmBalance {
    private static int balanceOfAtm;
    private BanknotesStore banknotesStore = new BanknotesStoreImpl();

    @Override
    public void withdraw(AtmLogic atmLogic, int balanceOfAccount, int withdrawAmount) {
        if (withdrawAmount > balanceOfAccount) {
            throw new RuntimeException("Not enough money for withdraw! Your balance: " + atmLogic.getBalanceOfAccount() + "₽");
        }
        else if (withdrawAmount > getBalanceOfAtm()) {
            throw new RuntimeException("Not enough money for withdraw! ATM balance: " + getBalanceOfAtm() + "₽");
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

    @Override
    public void getBalanceOfAtmAndAvailableBanknotes() {
        getBalanceOfAtm();
        System.out.println("Balance of ATM: " + balanceOfAtm + "₽");
        banknotesStore.getAvailableBanknotesForWithdraw();
    }

    public static int getBalanceOfAtm() {
        return balanceOfAtm = Banknotes.TEN.getBanknote() * BanknotesStoreImpl.getTenBanknotes()
                + Banknotes.FIFTY.getBanknote() * BanknotesStoreImpl.getFiftyBanknotes()
                + Banknotes.HUNDRED.getBanknote() * BanknotesStoreImpl.getHundredBanknotes()
                + Banknotes.TWO_HUNDRED.getBanknote() * BanknotesStoreImpl.getTwoHundredBanknotes()
                + Banknotes.FIVE_HUNDRED.getBanknote() * BanknotesStoreImpl.getFiveHundredBanknotes()
                + Banknotes.THOUSAND.getBanknote() * BanknotesStoreImpl.getThousandBanknotes()
                + Banknotes.TWO_THOUSAND.getBanknote() * BanknotesStoreImpl.getTwoThousandBanknotes()
                + Banknotes.FIVE_THOUSAND.getBanknote() * BanknotesStoreImpl.getFiveThousandBanknotes();
    }
}