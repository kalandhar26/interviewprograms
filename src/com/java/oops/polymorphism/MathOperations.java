package com.java.oops.polymorphism;

// Compile-time Polymorphism (Static Polymorphism) and Achieved via method overloading.
public class MathOperations {

    public static int add(int a, int b) {
        return a + b;
    }

    public static double add(double a, double b) {
        return a + b;
    }

    public static float add(float a, float b) {
        return a + b;
    }
}
