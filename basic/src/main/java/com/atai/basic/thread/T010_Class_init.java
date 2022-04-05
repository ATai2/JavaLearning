package com.atai.basic.thread;

public class T010_Class_init {
    public static void main(String[] args) {
        System.out.println(T.count);
    }

    static class T {

//        3
//        public static int count = 2;
//        public static T t = new T();

//        2
        public static T t = new T();
        public static int count = 2;

        private T() {
            count++;
        }
    }
}
