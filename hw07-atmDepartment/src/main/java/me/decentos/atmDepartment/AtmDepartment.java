package me.decentos.atmDepartment;

import me.decentos.atm.Atm;

public interface AtmDepartment {
    void getBalanceFromAllAtm();
    void resetAllAtmToDefaultState(); // через копирование
    Atm createAtm(int atmNumber);
}
