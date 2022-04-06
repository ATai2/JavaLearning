package com.atai.basic.juc;

import com.google.common.base.Stopwatch;

public class T002_CacheLinePadding {
    private static class T extends Padding {
        long x = 0L;

    }

    private static class Padding {

        volatile long x1, x2, x3, x4, x5, x6, x7;

    }

    private static T[] arr = new T[]{new T(), new T()};

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                arr[0].x = i;
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                arr[1].x = i;
            }
        });

        Stopwatch stopwatch = Stopwatch.createStarted();
        t1.start();
        ;
        t2.start();
        t1.join();
        t2.join();
        System.out.println(stopwatch.stop());
    }
}
