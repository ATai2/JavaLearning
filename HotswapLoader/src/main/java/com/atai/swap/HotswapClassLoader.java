package com.atai.swap;

public class HotswapClassLoader extends ClassLoader {
    public HotswapClassLoader() {
        super(HotswapClassLoader.class.getClassLoader());
    }

    public Class loadByte(byte[] classByte) {
        return defineClass(null, classByte, 0, classByte.length);
    }

}
