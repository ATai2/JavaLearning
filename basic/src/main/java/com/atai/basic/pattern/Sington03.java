package com.atai.basic.pattern;


/**
 * 懒汉模式
 * 并发核心volatile  防止指令重拍
 */
public class Sington03 {
    private static volatile Sington03 INSTANCE;

    private Sington03() {

    }

    public static Sington03 getInstance() {
        if (INSTANCE == null) {
            synchronized (Sington03.class) {
                if (INSTANCE == null) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    INSTANCE = new Sington03();
                }
            }
        }
        return INSTANCE;
    }

}
