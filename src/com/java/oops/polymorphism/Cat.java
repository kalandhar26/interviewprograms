package com.java.oops.polymorphism;

public class Cat implements SoundMaker {
    @Override
    public void makeSound() {
        System.out.println("Bark");
    }
}
