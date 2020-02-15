package me.decentos.banknotes;

import java.util.ArrayList;
import java.util.List;

public class BanknotesStoreImpl implements BanknotesStore {
    private static int tenBanknotes = 10;
    private static int fiftyBanknotes = 10;
    private static int hundredBanknotes = 10;
    private static int twoHundredBanknotes = 10;
    private static int fiveHundredBanknotes = 10;
    private static int thousandBanknotes = 10;
    private static int twoThousandBanknotes = 10;
    private static int fiveThousandBanknotes = 10;

    @Override
    public void receiveBanknotesForWithdrawal(int withdrawAmount) {
        List<Integer> banknotesForWithdrawal = new ArrayList<>();
        while (withdrawAmount != 0) {
            if (withdrawAmount / Banknotes.FIVE_THOUSAND.getBanknote() > 0 && fiveThousandBanknotes > 0) {
                withdrawAmount -= Banknotes.FIVE_THOUSAND.getBanknote();
                minusBanknotesCount(Banknotes.FIVE_THOUSAND);
                banknotesForWithdrawal.add(Banknotes.FIVE_THOUSAND.getBanknote());
            }
            else if (withdrawAmount / Banknotes.TWO_THOUSAND.getBanknote() > 0 && twoThousandBanknotes > 0) {
                withdrawAmount -= Banknotes.TWO_THOUSAND.getBanknote();
                minusBanknotesCount(Banknotes.TWO_THOUSAND);
                banknotesForWithdrawal.add(Banknotes.TWO_THOUSAND.getBanknote());
            }
            else if (withdrawAmount / Banknotes.THOUSAND.getBanknote() > 0 && thousandBanknotes > 0) {
                withdrawAmount -= Banknotes.THOUSAND.getBanknote();
                minusBanknotesCount(Banknotes.THOUSAND);
                banknotesForWithdrawal.add(Banknotes.THOUSAND.getBanknote());
            }
            else if (withdrawAmount / Banknotes.FIVE_HUNDRED.getBanknote() > 0  && fiveHundredBanknotes > 0) {
                withdrawAmount -= Banknotes.FIVE_HUNDRED.getBanknote();
                minusBanknotesCount(Banknotes.FIVE_HUNDRED);
                banknotesForWithdrawal.add(Banknotes.FIVE_HUNDRED.getBanknote());
            }
            else if (withdrawAmount / Banknotes.TWO_HUNDRED.getBanknote() > 0 && twoHundredBanknotes > 0) {
                withdrawAmount -= Banknotes.TWO_HUNDRED.getBanknote();
                minusBanknotesCount(Banknotes.TWO_HUNDRED);
                banknotesForWithdrawal.add(Banknotes.TWO_HUNDRED.getBanknote());
            }
            else if (withdrawAmount / Banknotes.HUNDRED.getBanknote() > 0 && hundredBanknotes > 0) {
                withdrawAmount -= Banknotes.HUNDRED.getBanknote();
                minusBanknotesCount(Banknotes.HUNDRED);
                banknotesForWithdrawal.add(Banknotes.HUNDRED.getBanknote());
            }
            else if (withdrawAmount / Banknotes.FIFTY.getBanknote() > 0 && fiftyBanknotes > 0) {
                withdrawAmount -= Banknotes.FIFTY.getBanknote();
                minusBanknotesCount(Banknotes.FIFTY);
                banknotesForWithdrawal.add(Banknotes.FIFTY.getBanknote());
            }
            else if (withdrawAmount / Banknotes.TEN.getBanknote() > 0 && tenBanknotes > 0) {
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
                tenBanknotes++;
                break;
            case FIFTY:
                fiftyBanknotes++;
                break;
            case HUNDRED:
                hundredBanknotes++;
                break;
            case TWO_HUNDRED:
                twoHundredBanknotes++;
                break;
            case FIVE_HUNDRED:
                fiveHundredBanknotes++;
                break;
            case THOUSAND:
                thousandBanknotes++;
                break;
            case TWO_THOUSAND:
                twoThousandBanknotes++;
                break;
            case FIVE_THOUSAND:
                fiveThousandBanknotes++;
                break;
        }
    }

    @Override
    public void minusBanknotesCount(Banknotes banknote) {
        switch (banknote)
        {
            case TEN:
                tenBanknotes--;
                break;
            case FIFTY:
                fiftyBanknotes--;
                break;
            case HUNDRED:
                hundredBanknotes--;
                break;
            case TWO_HUNDRED:
                twoHundredBanknotes--;
                break;
            case FIVE_HUNDRED:
                fiveHundredBanknotes--;
                break;
            case THOUSAND:
                thousandBanknotes--;
                break;
            case TWO_THOUSAND:
                twoThousandBanknotes--;
                break;
            case FIVE_THOUSAND:
                fiveThousandBanknotes--;
                break;
        }
    }

    @Override
    public void getAvailableBanknotesForWithdraw() {
        StringBuilder availableBanknotes = new StringBuilder("The following banknotes are available for withdrawal:\n");
        if (tenBanknotes > 0) availableBanknotes.append("\t").append(Banknotes.TEN.getBanknote()).append("₽ = ").append(tenBanknotes).append(" pcs.\n");
        if (fiftyBanknotes > 0) availableBanknotes.append("\t").append(Banknotes.FIFTY.getBanknote()).append("₽ = ").append(fiftyBanknotes).append(" pcs.\n");
        if (hundredBanknotes > 0) availableBanknotes.append("\t").append(Banknotes.HUNDRED.getBanknote()).append("₽ = ").append(hundredBanknotes).append(" pcs.\n");
        if (twoHundredBanknotes > 0) availableBanknotes.append("\t").append(Banknotes.TWO_HUNDRED.getBanknote()).append("₽ = ").append(twoHundredBanknotes).append(" pcs.\n");
        if (fiveHundredBanknotes > 0) availableBanknotes.append("\t").append(Banknotes.FIVE_HUNDRED.getBanknote()).append("₽ = ").append(fiveHundredBanknotes).append(" pcs.\n");
        if (thousandBanknotes > 0) availableBanknotes.append("\t").append(Banknotes.THOUSAND.getBanknote()).append("₽ = ").append(thousandBanknotes).append(" pcs.\n");
        if (twoThousandBanknotes > 0) availableBanknotes.append("\t").append(Banknotes.TWO_THOUSAND.getBanknote()).append("₽ = ").append(twoThousandBanknotes).append(" pcs.\n");
        if (fiveThousandBanknotes > 0) availableBanknotes.append("\t").append(Banknotes.FIVE_THOUSAND.getBanknote()).append("₽ = ").append(fiveThousandBanknotes).append(" pcs.");
        System.out.println(availableBanknotes);
    }

    public static int getTenBanknotes() {
        return tenBanknotes;
    }

    public static int getFiftyBanknotes() {
        return fiftyBanknotes;
    }

    public static int getHundredBanknotes() {
        return hundredBanknotes;
    }

    public static int getTwoHundredBanknotes() {
        return twoHundredBanknotes;
    }

    public static int getFiveHundredBanknotes() {
        return fiveHundredBanknotes;
    }

    public static int getThousandBanknotes() {
        return thousandBanknotes;
    }

    public static int getTwoThousandBanknotes() {
        return twoThousandBanknotes;
    }

    public static int getFiveThousandBanknotes() {
        return fiveThousandBanknotes;
    }
}