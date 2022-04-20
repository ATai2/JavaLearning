package com.atai.swap;

import java.net.URL;
import java.net.URLClassLoader;

public class JdbcUrlClassLoader extends URLClassLoader {
    public JdbcUrlClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

}
