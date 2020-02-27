package me.decentos.atmDepartment;

import me.decentos.atm.Atm;
import me.decentos.mementoPattern.Memento;
import me.decentos.mementoPattern.MementoImpl;

import java.util.ArrayList;
import java.util.List;

public class AtmDepartmentImpl implements AtmDepartment {
    private List<Atm> atmList = new ArrayList<>();
    private Memento memento = new MementoImpl();

    @Override
    public void getBalanceFromAllAtm() {
        int balanceOfAllAtm = 0;
        for (Atm atm : atmList) {
            balanceOfAllAtm += atm.getBalanceOfAtm("passForAdmin");
        }
        System.out.println("Balance of all ATM's: " + balanceOfAllAtm + "₽");
    }

    @Override
    public void resetAllAtmToDefaultState() {
        atmList = memento.restore();
    }

    @Override
    public Atm createAtm(int fillCount) {
        Atm atm = new Atm(fillCount);
        atmList.add(atm);
        memento.makeCopy(new Atm(fillCount));
        return atm;
    }
}