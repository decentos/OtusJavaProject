package me.decentos.observerPattern;

import java.util.ArrayList;
import java.util.List;

public class EventProducer {
    private final List<Listener> listeners = new ArrayList<>();

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    public void removeAllListeners() {
        listeners.clear();
    }

    public int event() {
        int balance = 0;
        for (Listener listener : listeners) {
            balance += listener.getBalance();
        }
        return balance;
    }
}
