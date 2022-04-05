package com.atai.basic.thread;

import com.google.common.base.Stopwatch;

public class T009_intepret_compile {
    public static void main(String[] args) {
        for (int i = 0; i < 10_00000; i++) {
            m();
        }
        Stopwatch s=Stopwatch.createStarted();
        for (int i = 0; i < 10_00000; i++) {
            m();
        }
        System.out.println(s.stop());
    }
//    10_0000           10_00000
//    default 265.7 μs    1.080 ms
//    -Xint   626.9 ms    6.116 s
//    -Xcomp  2.066 ms    1.951 ms


    private static void m() {
        for (int i = 0; i < 1000; i++) {
            int i1 = i % 3;
        }
    }
}
