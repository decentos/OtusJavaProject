package me.decentos;

public class Demo {
    public static void main(String[] args) {
        new Demo().action();
    }

    public void action() {
        Logging logging = ProxyIoC.createMyClass();
        logging.calculation(6);
    }
}
