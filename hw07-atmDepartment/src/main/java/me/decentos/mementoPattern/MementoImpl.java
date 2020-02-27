package me.decentos.mementoPattern;

import me.decentos.atm.Atm;

import java.util.ArrayList;
import java.util.List;

public class MementoImpl implements Memento {
    private static List<Atm> listAtm = new ArrayList<>();

    @Override
    public void makeCopy(Atm atm) {
        listAtm.add(atm);
    }

    @Override
    public List<Atm> restore() {
        return listAtm;
    }
}