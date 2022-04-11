package com.atai.basic.pattern;

//饿汉式
public class Sington01 {
    //实例变量
    private byte[] data = new byte[1024];

    //在定义实例对象的时候直接初始化
    private static Sington01 instance = new Sington01();

    //私有构造函数，不允许外部new
    private Sington01() {
    }

    public static Sington01 getInstance() {
        return instance;
    }
}
