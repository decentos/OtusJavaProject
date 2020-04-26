package me.decentos;

import lombok.SneakyThrows;

public class StartSequence {
    private static final int LIMIT = 10;
    private String threadName = "second";

    public static void main(String[] args) {
        StartSequence seq = new StartSequence();
        new Thread(() -> seq.print(1, Thread.currentThread().getName()), "first").start();
        new Thread(() -> seq.print(1, Thread.currentThread().getName()), "second").start();
    }

    @SneakyThrows
    private synchronized void print(int count, String thisName) {
        for (int i = 0; i < LIMIT; i++) {

            while (thisName.equals(threadName)) {
                this.wait();
            }

            System.out.println(count);
            count++;
            threadName = thisName;
            Thread.sleep(1_000);
            notifyAll();
        }

        for (int i = 0; i < LIMIT - 1; i++) {

            while (thisName.equals(threadName)) {
                this.wait();
            }

            count--;
            System.out.println(count - 1);
            threadName = thisName;
            Thread.sleep(1_000);
            notifyAll();
        }
    }
}
