package com.atai.basic.thread;

public class T002_ThreadCount {
    private long count = 0;

    private void add10k() {
        int idx = 0;
        while (idx++ < 10000) {
            count += 1;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        T002_ThreadCount test = new T002_ThreadCount();
        final Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                test.add10k();
            }
        });
        final Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                test.add10k();
            }
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(test.count);

    }
}
