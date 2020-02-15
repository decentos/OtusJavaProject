package me.decentos.banknotes;

public enum Banknotes {
    TEN(10),
    FIFTY(50),
    HUNDRED(100),
    TWO_HUNDRED(200),
    FIVE_HUNDRED(500),
    THOUSAND(1000),
    TWO_THOUSAND(2000),
    FIVE_THOUSAND(5000);

    private int banknote;

    Banknotes(int banknote) {
        this.banknote = banknote;
    }

    public int getBanknote() {
        return banknote;
    }
}
