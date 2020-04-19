package me.decentos;

import lombok.SneakyThrows;

public class StartSequence {
    private int count = 1;
    private static final int LIMIT = 10;

    public static void main(String[] args) {
        StartSequence sequence = new StartSequence();
        sequence.go();
    }

    @SneakyThrows
    private void go() {
        Thread thread1 = new Thread(this::startSeq1);
        Thread thread2 = new Thread(this::startSeq2);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }

    @SneakyThrows
    private synchronized void startSeq1() {
        for (int i = 0; i < LIMIT; i++) {
            System.out.println(count);
            wait();
            count++;
            notify();
        }
        for (int i = 0; i < LIMIT - 1; i++) {
            count--;
            System.out.println(count - 1);
            wait();
            notify();
        }
    }

    @SneakyThrows
    private synchronized void startSeq2() {
        for (int i = 0; i < LIMIT; i++) {
            Thread.sleep(1000);
            System.out.println(count);
            notify();
            wait();
        }
        for (int i = 0; i < LIMIT - 1; i++) {
            Thread.sleep(1000);
            System.out.println(count - 1);
            notify();
            wait();
        }
    }
}
