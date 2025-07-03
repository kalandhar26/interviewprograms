package com.java.oops.polymorphism;

public class Dog extends Animal { // Tight coupling to Animal

    @Override
    void sound() {
        System.out.println("Dog barks");
    }

    @Override
    void makeSound() {
        System.out.println("Bark");
    }
}
