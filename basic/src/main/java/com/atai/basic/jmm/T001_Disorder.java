package com.atai.basic.jmm;

import java.util.concurrent.TimeUnit;

public class T001_Disorder {
    private static int x, y;
    private static int a, b;

    public static void main(String[] args) throws InterruptedException {
        int i = 0;
        for (; ; ) {
            i++;
            x = 0;
            y = 0;
            a = 0;
            b = 0;
            Thread one = new Thread(new Runnable() {
                @Override
                public void run() {
                    shortWait(10);
                    a = 1;
                    x = b;
                }
            });
            Thread other = new Thread(new Runnable() {
                @Override
                public void run() {
                    b = 1;
                    y = a;
                }
            });
            one.start();
            other.start();
            one.join();
            other.join();
            String resut = "第" + i + "" + "(" + x + "," + y + ")";
            if (x == 0 && y == 0) {
                System.err.println(resut);
                break;
            } else {
                System.out.println(resut);
            }


        }

    }

    private static void shortWait(int i) {
        try {
            TimeUnit.MICROSECONDS.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
