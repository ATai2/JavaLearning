package com.atai.basic.juc;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.currentThread;
import static java.util.concurrent.ThreadLocalRandom.current;

public class T006_Exchanger {
    public static void main(String[] args) {
// 定义Exchanger类，该类是一个泛型类，String类型标明一对线程交换的数据只能是String类型
        final Exchanger<String> exchanger = new Exchanger<>();
        // 定义线程T1
        new Thread(() ->
        {
            System.out.println(currentThread() + " start.");
            try {
                // 随机休眠1～10秒钟
                randomSleep();
                // ①执行exchange方法，将对应的数据传递给T2线程，同时从T2线程获取交换的数据
                String data = exchanger.exchange("I am from T1");
// data就是从T2线程中返回的数据
                System.out.println(currentThread() + " received: " + data);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(currentThread() + " end.");
        }, "T1").start();

// 原理同T1线程，省略注释内容...
        new Thread(() ->
        {
            System.out.println(currentThread() + " start.");
            try {
                randomSleep();
                String data = exchanger.exchange("I am from T2");
                System.out.println(currentThread() + " received: " + data);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(currentThread() + " end.");
        }, "T2").start();
    }

    private static void randomSleep() {
        try {
            TimeUnit.SECONDS.sleep(current().nextInt(10));
        } catch (InterruptedException e) {
            // ignore
        }
    }
}
