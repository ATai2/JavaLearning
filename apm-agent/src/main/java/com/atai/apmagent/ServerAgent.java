package com.atai.apmagent;

import javassist.*;
import javassist.bytecode.AccessFlag;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
import java.util.Arrays;

public class ServerAgent {
    public static void premain(String args, Instrumentation instrumentation) {
        System.out.println(" server filtering   ----");

        WildcardMatcher matcher = new WildcardMatcher(args);

        instrumentation.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {
//                System.out.println(className);
                if (className == null || loader == null) {
                    return null;
                }
                if (!matcher.maches(className.replaceAll("/", "."))) {
                    return null;
                }
                try {
                    return buildMonitorBytes(loader, className.replaceAll("/", "."));
                } catch (NotFoundException | CannotCompileException | IOException e) {
                    throw new RuntimeException(e);
                }

            }
        });
    }

    //    改造一个类
    private static byte[] buildMonitorBytes(ClassLoader loader, String className) throws NotFoundException, CannotCompileException, IOException {
        ClassPool pool = new ClassPool();
        pool.insertClassPath(new LoaderClassPath(loader));

        CtClass ctClass = pool.get(className);
        CtMethod[] declaredMethods = ctClass.getDeclaredMethods();
        for (CtMethod method :
                declaredMethods) {
            if (!AccessFlag.isPublic(method.getModifiers())) {
                continue;
            }

            CtMethod copyMethod = CtNewMethod.copy(method, ctClass, new ClassMap());
            method.setName(method.getName() + "$agent");
            if (copyMethod.getReturnType().getName().equals("void")) {
                copyMethod.setBody("{\n" +
                        "                Object trace = com.atai.apmagent.ServerAgent.begin($args);\n" +
                        "                System.out.println(\" server bbb   ----\");" +
                        "                try{\n" +
                        "                      " + copyMethod.getName() + "$agent($$);\n" +
                        "                }finally {\n" +
                        "                    com.atai.apmagent.ServerAgent.end(trace);\n" +
                        "                }\n" +
                        "            }");
            } else {
                copyMethod.setBody("{\n" +
                        "                Object trace = com.atai.apmagent.ServerAgent.begin($args);\n" +
                        "                try{\n" +
                        "                    return " + copyMethod.getName() + "$agent($$);\n" +
                        "                }finally {\n" +
                        "                    com.atai.apmagent.ServerAgent.end(trace);\n" +
                        "                }\n" +
                        "            }");
            }
            ctClass.addMethod(copyMethod);
        }
        return ctClass.toBytecode();
    }

    public static Object begin(Object[] args) {
        return new TraceInfo(System.currentTimeMillis(), args);
    }

    public static void end(Object trace) {
        TraceInfo traceInfo = (TraceInfo) trace;
        System.out.println("参数：" + Arrays.toString(traceInfo.args));
        System.out.println("用时：" + (System.currentTimeMillis() - traceInfo.time));
    }
}
