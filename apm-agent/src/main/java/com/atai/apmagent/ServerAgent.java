package com.atai.apmagent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

public class ServerAgent {
    public static void main(String args, Instrumentation instrumentation) {
        System.out.println(" server filtering   ----");

        WildcardMatcher matcher = new WildcardMatcher();

        instrumentation.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
                if (className == null || loader == null) {
                    return null;
                }
                if (!matcher.maches(className)) {
                    return null;
                }



                return new byte[0];
            }
        });

    }
}
