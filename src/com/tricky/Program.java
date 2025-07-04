package com.tricky;

public class Program {

    public static void main(String[] args) {
        invokeMethod(null);
    }

    public static void invokeMethod(Object obj){
        System.out.println("Object");
    }

    public static void invokeMethod(String obj){
        System.out.println("String");
    }
}
