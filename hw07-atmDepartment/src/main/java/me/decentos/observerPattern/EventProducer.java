package me.decentos.observerPattern;

import java.util.ArrayList;
import java.util.List;

public class EventProducer {
    private final List<Balance> balances = new ArrayList<>();

    public void addListener(Balance balance) {
        balances.add(balance);
    }

    public void removeAllListeners() {
        balances.clear();
    }

    public int getBalanceOfAllAtm() {
        int balanceOfAllAtm = 0;
        for (Balance balance : balances) {
            balanceOfAllAtm += balance.getBalance();
        }
        return balanceOfAllAtm;
    }
}
