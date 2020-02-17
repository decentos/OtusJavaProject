package me.decentos.banknotes;

import java.util.*;

public class BanknotesStoreImpl implements BanknotesStore {
    private static Map<Banknotes, Integer> banknotes = new HashMap<>();

    public void fillAtmByBanknotes() {
        for (int i = 0; i < 10; i++) {
            banknotes.put(Banknotes.TEN, 10);
            banknotes.put(Banknotes.FIFTY, 10);
            banknotes.put(Banknotes.HUNDRED, 10);
            banknotes.put(Banknotes.TWO_HUNDRED, 10);
            banknotes.put(Banknotes.FIVE_HUNDRED, 10);
            banknotes.put(Banknotes.THOUSAND, 10);
            banknotes.put(Banknotes.TWO_THOUSAND, 10);
            banknotes.put(Banknotes.FIVE_THOUSAND, 10);
        }
    }

    @Override
    public void receiveBanknotesForWithdrawal(int withdrawAmount) {
        Arrays.sort(Banknotes.values());
        List<Integer> banknotesForWithdrawal = new ArrayList<>();
        int i = Banknotes.values().length - 1;
        while (withdrawAmount != 0) {
            if (i < 0)
                throw new RuntimeException("There are not enough banknotes in the ATM to withdraw the this amount. Please enter an another amount.");

            for (; i >= 0 ; i--) {
                if (withdrawAmount / Banknotes.values()[i].getBanknote() > 0 && banknotes.get(Banknotes.values()[i]) > 0) {
                    withdrawAmount -= Banknotes.values()[i].getBanknote();
                    minusBanknotesCount(Banknotes.values()[i]);
                    banknotesForWithdrawal.add(Banknotes.values()[i].getBanknote());
                    if (withdrawAmount == 0) break;
                    i++;
                }
            }
        }
        System.out.println("You received: " + banknotesForWithdrawal);
    }

    @Override
    public void plusBanknotesCount(Banknotes banknote) {
        switch (banknote)
        {
            case TEN:
                banknotes.put(Banknotes.TEN, banknotes.get(Banknotes.TEN) + 1);
                break;
            case FIFTY:
                banknotes.put(Banknotes.FIFTY, banknotes.get(Banknotes.FIFTY) + 1);
                break;
            case HUNDRED:
                banknotes.put(Banknotes.HUNDRED, banknotes.get(Banknotes.HUNDRED) + 1);
                break;
            case TWO_HUNDRED:
                banknotes.put(Banknotes.TWO_HUNDRED, banknotes.get(Banknotes.TWO_HUNDRED) + 1);
                break;
            case FIVE_HUNDRED:
                banknotes.put(Banknotes.FIVE_HUNDRED, banknotes.get(Banknotes.FIVE_HUNDRED) + 1);
                break;
            case THOUSAND:
                banknotes.put(Banknotes.THOUSAND, banknotes.get(Banknotes.THOUSAND) + 1);
                break;
            case TWO_THOUSAND:
                banknotes.put(Banknotes.TWO_THOUSAND, banknotes.get(Banknotes.TWO_THOUSAND) + 1);
                break;
            case FIVE_THOUSAND:
                banknotes.put(Banknotes.FIVE_THOUSAND, banknotes.get(Banknotes.FIVE_THOUSAND) + 1);
                break;
        }
    }

    @Override
    public void minusBanknotesCount(Banknotes banknote) {
        switch (banknote)
        {
            case TEN:
                banknotes.put(Banknotes.TEN, banknotes.get(Banknotes.TEN) - 1);
                break;
            case FIFTY:
                banknotes.put(Banknotes.FIFTY, banknotes.get(Banknotes.FIFTY) - 1);
                break;
            case HUNDRED:
                banknotes.put(Banknotes.HUNDRED, banknotes.get(Banknotes.HUNDRED) - 1);
                break;
            case TWO_HUNDRED:
                banknotes.put(Banknotes.TWO_HUNDRED, banknotes.get(Banknotes.TWO_HUNDRED) - 1);
                break;
            case FIVE_HUNDRED:
                banknotes.put(Banknotes.FIVE_HUNDRED, banknotes.get(Banknotes.FIVE_HUNDRED) - 1);
                break;
            case THOUSAND:
                banknotes.put(Banknotes.THOUSAND, banknotes.get(Banknotes.THOUSAND) - 1);
                break;
            case TWO_THOUSAND:
                banknotes.put(Banknotes.TWO_THOUSAND, banknotes.get(Banknotes.TWO_THOUSAND) - 1);
                break;
            case FIVE_THOUSAND:
                banknotes.put(Banknotes.FIVE_THOUSAND, banknotes.get(Banknotes.FIVE_THOUSAND) - 1);
                break;
        }
    }

    @Override
    public void getAvailableBanknotesForWithdraw() {
        StringBuilder availableBanknotes = new StringBuilder("The following banknotes are available for withdrawal:\n");
        if (banknotes.get(Banknotes.TEN) > 0) availableBanknotes.append("\t").append(Banknotes.TEN.getBanknote()).append("₽ = ").append(banknotes.get(Banknotes.TEN)).append(" pcs.\n");
        if (banknotes.get(Banknotes.FIFTY) > 0) availableBanknotes.append("\t").append(Banknotes.FIFTY.getBanknote()).append("₽ = ").append(banknotes.get(Banknotes.FIFTY)).append(" pcs.\n");
        if (banknotes.get(Banknotes.HUNDRED) > 0) availableBanknotes.append("\t").append(Banknotes.HUNDRED.getBanknote()).append("₽ = ").append(banknotes.get(Banknotes.HUNDRED)).append(" pcs.\n");
        if (banknotes.get(Banknotes.TWO_HUNDRED) > 0) availableBanknotes.append("\t").append(Banknotes.TWO_HUNDRED.getBanknote()).append("₽ = ").append(banknotes.get(Banknotes.TWO_HUNDRED)).append(" pcs.\n");
        if (banknotes.get(Banknotes.FIVE_HUNDRED) > 0) availableBanknotes.append("\t").append(Banknotes.FIVE_HUNDRED.getBanknote()).append("₽ = ").append(banknotes.get(Banknotes.FIVE_HUNDRED)).append(" pcs.\n");
        if (banknotes.get(Banknotes.THOUSAND) > 0) availableBanknotes.append("\t").append(Banknotes.THOUSAND.getBanknote()).append("₽ = ").append(banknotes.get(Banknotes.THOUSAND)).append(" pcs.\n");
        if (banknotes.get(Banknotes.TWO_THOUSAND) > 0) availableBanknotes.append("\t").append(Banknotes.TWO_THOUSAND.getBanknote()).append("₽ = ").append(banknotes.get(Banknotes.TWO_THOUSAND)).append(" pcs.\n");
        if (banknotes.get(Banknotes.FIVE_THOUSAND) > 0) availableBanknotes.append("\t").append(Banknotes.FIVE_THOUSAND.getBanknote()).append("₽ = ").append(banknotes.get(Banknotes.FIVE_THOUSAND)).append(" pcs.");
        System.out.println(availableBanknotes);
    }

    public static int getTenBanknotes() {
        return banknotes.get(Banknotes.TEN);
    }

    public static int getFiftyBanknotes() {
        return banknotes.get(Banknotes.FIFTY);
    }

    public static int getHundredBanknotes() {
        return banknotes.get(Banknotes.HUNDRED);
    }

    public static int getTwoHundredBanknotes() {
        return banknotes.get(Banknotes.TWO_HUNDRED);
    }

    public static int getFiveHundredBanknotes() {
        return banknotes.get(Banknotes.FIVE_HUNDRED);
    }

    public static int getThousandBanknotes() {
        return banknotes.get(Banknotes.THOUSAND);
    }

    public static int getTwoThousandBanknotes() {
        return banknotes.get(Banknotes.TWO_THOUSAND);
    }

    public static int getFiveThousandBanknotes() {
        return banknotes.get(Banknotes.FIVE_THOUSAND);
    }
}