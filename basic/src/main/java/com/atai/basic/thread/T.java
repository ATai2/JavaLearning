package com.atai.basic.thread;

public class T {
    private int count = 0;
    private static Object o = new Object();

    public void m() {
        synchronized (o) {
            count++;
            System.out.println("count:" + count);
        }
    }
}
