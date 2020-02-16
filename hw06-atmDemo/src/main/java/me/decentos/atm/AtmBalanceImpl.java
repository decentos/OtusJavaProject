package me.decentos.atm;

import me.decentos.banknotes.Banknotes;
import me.decentos.banknotes.BanknotesStore;
import me.decentos.banknotes.BanknotesStoreImpl;

public class AtmBalanceImpl implements AtmBalance {
    private static int balanceOfAtm;
    private BanknotesStore banknotesStore = new BanknotesStoreImpl();

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
