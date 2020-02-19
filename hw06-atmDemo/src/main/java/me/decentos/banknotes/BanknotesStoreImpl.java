package me.decentos.banknotes;

import java.util.*;

public class BanknotesStoreImpl implements BanknotesStore {
    private static Map<Banknotes, Integer> banknotes = new HashMap<>();

    public void fillAtmByBanknotes() {
        for (Banknotes item : Banknotes.values()) {
            banknotes.put(item, 10);
        }
    }

    @Override
    public void receiveBanknotesForWithdrawal(int withdrawAmount) {
        Arrays.sort(Banknotes.values());
        List<Integer> banknotesForWithdrawal = new ArrayList<>();

        for (int i = Banknotes.values().length - 1; i >= 0 ; i--) {
            if (withdrawAmount / Banknotes.values()[i].getBanknote() > 0 && banknotes.get(Banknotes.values()[i]) > 0) {
                withdrawAmount -= Banknotes.values()[i].getBanknote();
                minusBanknotesCount(Banknotes.values()[i]);
                banknotesForWithdrawal.add(Banknotes.values()[i].getBanknote());
                if (withdrawAmount == 0) break;
                i++;
            }
        }
        if (withdrawAmount != 0)
            throw new RuntimeException("There are not enough banknotes in the ATM to withdraw the this amount. Please enter an another amount.");

        System.out.println("You received: " + banknotesForWithdrawal);
    }

    @Override
    public void plusBanknotesCount(Banknotes banknote) {
        banknotes.put(banknote, banknotes.get(banknote) + 1);
    }

    @Override
    public void minusBanknotesCount(Banknotes banknote) {
        banknotes.put(banknote, banknotes.get(banknote) - 1);
    }

    @Override
    public void getAvailableBanknotesForWithdraw() {
        StringBuilder availableBanknotes = new StringBuilder("The following banknotes are available for withdrawal:\n");
        for (Banknotes item : Banknotes.values()) {
            if (banknotes.get(item) > 0) {
                availableBanknotes.append("\t").append(item.getBanknote()).append("₽ = ").append(banknotes.get(item)).append(" pcs.\n");
            }
        }
        System.out.println(availableBanknotes);
    }

    public static Map<Banknotes, Integer> getBanknotes() {
        return banknotes;
    }
}