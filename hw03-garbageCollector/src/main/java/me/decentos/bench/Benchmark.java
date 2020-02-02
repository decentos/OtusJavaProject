package me.decentos.bench;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Benchmark implements BenchmarkMBean {
    private final int loopCounter;
    private volatile int size = 0;

    public Benchmark(int loopCounter) {
        this.loopCounter = loopCounter;
    }

    void run() throws InterruptedException {
        Random random = new Random();
        for (int idx = 0; idx < loopCounter; idx++) {
            int local = size;
            List<Object> list = new ArrayList<>(local);
            for (int i = 0; i < local; i++) {
                list.add(new Integer(random.nextInt()));
//                if (i % 1000 == 0) {
//                    list.remove(0);
//                }
            }
            Thread.sleep(10);
        }
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void setSize(int size) {
        System.out.println("new size:" + size);
        this.size = size;
    }

}