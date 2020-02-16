package me.decentos.banknotes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        List<Integer> banknotesForWithdrawal = new ArrayList<>();
        while (withdrawAmount != 0) {
            if (withdrawAmount / Banknotes.FIVE_THOUSAND.getBanknote() > 0 && banknotes.get(Banknotes.FIVE_THOUSAND) > 0) {
                withdrawAmount -= Banknotes.FIVE_THOUSAND.getBanknote();
                minusBanknotesCount(Banknotes.FIVE_THOUSAND);
                banknotesForWithdrawal.add(Banknotes.FIVE_THOUSAND.getBanknote());
            }
            else if (withdrawAmount / Banknotes.TWO_THOUSAND.getBanknote() > 0 && banknotes.get(Banknotes.TWO_THOUSAND) > 0) {
                withdrawAmount -= Banknotes.TWO_THOUSAND.getBanknote();
                minusBanknotesCount(Banknotes.TWO_THOUSAND);
                banknotesForWithdrawal.add(Banknotes.TWO_THOUSAND.getBanknote());
            }
            else if (withdrawAmount / Banknotes.THOUSAND.getBanknote() > 0 && banknotes.get(Banknotes.THOUSAND) > 0) {
                withdrawAmount -= Banknotes.THOUSAND.getBanknote();
                minusBanknotesCount(Banknotes.THOUSAND);
                banknotesForWithdrawal.add(Banknotes.THOUSAND.getBanknote());
            }
            else if (withdrawAmount / Banknotes.FIVE_HUNDRED.getBanknote() > 0  && banknotes.get(Banknotes.FIVE_HUNDRED) > 0) {
                withdrawAmount -= Banknotes.FIVE_HUNDRED.getBanknote();
                minusBanknotesCount(Banknotes.FIVE_HUNDRED);
                banknotesForWithdrawal.add(Banknotes.FIVE_HUNDRED.getBanknote());
            }
            else if (withdrawAmount / Banknotes.TWO_HUNDRED.getBanknote() > 0 && banknotes.get(Banknotes.TWO_HUNDRED) > 0) {
                withdrawAmount -= Banknotes.TWO_HUNDRED.getBanknote();
                minusBanknotesCount(Banknotes.TWO_HUNDRED);
                banknotesForWithdrawal.add(Banknotes.TWO_HUNDRED.getBanknote());
            }
            else if (withdrawAmount / Banknotes.HUNDRED.getBanknote() > 0 && banknotes.get(Banknotes.HUNDRED) > 0) {
                withdrawAmount -= Banknotes.HUNDRED.getBanknote();
                minusBanknotesCount(Banknotes.HUNDRED);
                banknotesForWithdrawal.add(Banknotes.HUNDRED.getBanknote());
            }
            else if (withdrawAmount / Banknotes.FIFTY.getBanknote() > 0 && banknotes.get(Banknotes.FIFTY) > 0) {
                withdrawAmount -= Banknotes.FIFTY.getBanknote();
                minusBanknotesCount(Banknotes.FIFTY);
                banknotesForWithdrawal.add(Banknotes.FIFTY.getBanknote());
            }
            else if (withdrawAmount / Banknotes.TEN.getBanknote() > 0 && banknotes.get(Banknotes.TEN) > 0) {
                withdrawAmount -= Banknotes.TEN.getBanknote();
                minusBanknotesCount(Banknotes.TEN);
                banknotesForWithdrawal.add(Banknotes.TEN.getBanknote());
            }
            else {
                throw new RuntimeException("There are not enough banknotes in the ATM to withdraw the this amount. Please enter an another amount.");
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