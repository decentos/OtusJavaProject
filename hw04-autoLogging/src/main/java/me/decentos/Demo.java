package me.decentos;

public class Demo {
    public static void main(String[] args) {
        try {
            new Demo().action();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public void action() throws NoSuchMethodException {
        Logging logging = ProxyIoC.createMyClass();
        logging.calculation(6);
    }
}