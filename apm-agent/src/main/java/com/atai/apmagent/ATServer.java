package com.atai.apmagent;

import java.util.concurrent.TimeUnit;

public class ATServer {
    public Integer sayHello(String name, String message) {
        System.out.println("Hello v2");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

}
