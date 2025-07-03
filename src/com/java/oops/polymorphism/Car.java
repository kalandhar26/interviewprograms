package com.java.oops.polymorphism;

public class Car implements SoundMaker{
    @Override
    public void makeSound() {
        System.out.println("Horn.");
    }
}
