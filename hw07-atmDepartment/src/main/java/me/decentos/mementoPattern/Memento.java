package me.decentos.mementoPattern;

import me.decentos.atm.Atm;

import java.util.List;

public interface Memento {
    void makeCopy(Atm atm);
    List<Atm> restore();
}