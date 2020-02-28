package me.decentos.atmDepartment;

import me.decentos.atm.Atm;
import me.decentos.mementoPattern.Memento;
import me.decentos.mementoPattern.MementoImpl;
import me.decentos.observerPattern.EventProducer;

public class AtmDepartmentImpl implements AtmDepartment {
    private Memento memento = new MementoImpl();
    private EventProducer producer = new EventProducer();

    @Override
    public void getBalanceFromAllAtm() {
        System.out.println("Balance of all ATM's: " + producer.event() + "₽");
    }

    @Override
    public void resetAllAtmToDefaultState() {
        producer.removeAllListeners();
        for (Atm atm : memento.restore()) {
            producer.addListener(atm.getListener());
        }
    }

    @Override
    public Atm createAtm(int fillCount) {
        Atm atm = new Atm(fillCount);
        producer.addListener(atm.getListener());
        memento.makeCopy(new Atm(fillCount));
        return atm;
    }
}