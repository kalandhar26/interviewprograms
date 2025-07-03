package com.java.designPatterns.singleton;

public class Singleton {

    public static volatile Singleton instance;

    private Singleton(){
        // Private constructor to prevent instantiation
    }

    private Singleton getInstance(){
        if(instance == null){
            synchronized (Singleton.class){
                if(instance==null){
                    instance = new Singleton();
                }
            }
        }

        return instance;
    }


}
