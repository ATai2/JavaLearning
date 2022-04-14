package com.atai.apmagent;

import javassist.ClassPool;

import java.lang.instrument.Instrumentation;

public class AtaiAgent {

    public static void premain(String args, Instrumentation instrumentation) {
        System.out.println("hello javaagent premain:" + args);
//        ClassPool pool = ClassPool.getDefault();

    }
}
