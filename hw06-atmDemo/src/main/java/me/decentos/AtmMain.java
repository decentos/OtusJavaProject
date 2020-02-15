package me.decentos;

import me.decentos.banknotes.Banknotes;

public class AtmMain {
    public static void main(String[] args) {
        AtmLogic atmLogic = new AtmLogic(20_000);
        atmLogic.getBalanceOfAtm();
        System.out.println("Balance of account: " + atmLogic.getBalanceOfAccount() + "₽");
        atmLogic.deposit(Banknotes.FIFTY, Banknotes.FIVE_HUNDRED);
        atmLogic.getBalanceOfAtm();
        atmLogic.withdraw(10_000);
        atmLogic.getBalanceOfAtm();
        atmLogic.withdraw(8_860);
        atmLogic.withdraw(90);
        atmLogic.getBalanceOfAtm();
//        atmLogic.withdraw(42);
//        atmLogic.withdraw(40_000);

//        AtmLogic atmLogic2 = new AtmLogic(100_000);
//        atmLogic2.getBalanceOfAtm();
//        atmLogic2.withdraw(82_000);
//        atmLogic2.withdraw(40);
//        atmLogic2.withdraw(40);
//        atmLogic2.getBalanceOfAtm();
//        atmLogic2.withdraw(30);
    }
}
