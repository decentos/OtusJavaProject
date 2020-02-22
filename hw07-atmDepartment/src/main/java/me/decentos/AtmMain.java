package me.decentos;

import me.decentos.atm.Atm;
import me.decentos.atmDepartment.AtmDepartment;
import me.decentos.atmDepartment.AtmDepartmentImpl;
import me.decentos.banknotes.Banknotes;

public class AtmMain {
    public static void main(String[] args) {
//        AtmLogic atmLogic = new AtmLogic();
//        atmLogic.startUseAtm(20_000);
//        atmLogic.getBalanceOfAtmAndAvailableBanknotes("passForAdmin");
//        System.out.println("Balance of account: " + atmLogic.getBalanceOfAccount() + "₽");
//        atmLogic.deposit(Banknotes.FIFTY, Banknotes.FIVE_HUNDRED);
//        atmLogic.getBalanceOfAtmAndAvailableBanknotes("passForAdmin");
//        atmLogic.withdraw(10_000);
//        atmLogic.withdraw(8_860);
//        atmLogic.withdraw(90);
//        atmLogic.getBalanceOfAtmAndAvailableBanknotes("passForAdmin");
//        atmLogic.deposit(Banknotes.FIFTY, Banknotes.FIFTY, Banknotes.FIVE_HUNDRED, Banknotes.THOUSAND);
//        atmLogic.getBalanceOfAtmAndAvailableBanknotes("passForAdmin");
//        atmLogic.getBalanceOfAtmAndAvailableBanknotes("123");
//        atmLogic.withdraw(42);
//        atmLogic.withdraw(40_000);

//        AtmLogic atmLogic2 = new AtmLogic(100_000);
//        atmLogic2.getBalanceOfAtmAndAvailableBanknotes("passForAdmin");
//        atmLogic2.withdraw(82_000);
//        atmLogic2.withdraw(40);
//        atmLogic2.withdraw(40);
//        atmLogic2.getBalanceOfAtmAndAvailableBanknotes("passForAdmin");
//        atmLogic2.getBalanceOfAtmAndAvailableBanknotes("123");
//        atmLogic2.withdraw(30);

        AtmDepartment atmDepartment = new AtmDepartmentImpl();
        atmDepartment.getBalanceFromAllAtm();
        System.out.println("=========================");

        Atm atm = atmDepartment.createAtm(123);
        atmDepartment.getBalanceFromAllAtm();
        System.out.println("=========================");

        atm.getBalanceOfAtmAndAvailableBanknotes("passForAdmin");
        atm.startUseAtm(30_000);
        System.out.println("Balance of account 1: " + atm.getBalanceOfAccount());
        System.out.println("=========================");
        atm.deposit(Banknotes.FIFTY, Banknotes.FIVE_HUNDRED);
        atm.withdraw(10_000);
        atm.withdraw(8_860);
        atm.withdraw(90);
        atm.getBalanceOfAtmAndAvailableBanknotes("passForAdmin");
        atmDepartment.getBalanceFromAllAtm();
        System.out.println("=========================");

        Atm atm2 = atmDepartment.createAtm(1234);
        atm2.getBalanceOfAtmAndAvailableBanknotes("passForAdmin");
        atm2.startUseAtm(20_000);
        System.out.println("Balance of account 2: " + atm2.getBalanceOfAccount());

        atmDepartment.getBalanceFromAllAtm();

    }
}
