package me.decentos.atmDepartment;

import me.decentos.atm.Atm;

import java.util.ArrayList;
import java.util.List;

public class AtmDepartmentImpl implements AtmDepartment {
    private List<Atm> atmList = new ArrayList<>();

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
        for (Atm atm : atmList) {
            atm.resetAtmToDefaultState();
        }
    }

    @Override
    public Atm createAtm(int atmNumber) {
        Atm atm = new Atm(atmNumber);
        atmList.add(atm);
        return atm;
    }
}
