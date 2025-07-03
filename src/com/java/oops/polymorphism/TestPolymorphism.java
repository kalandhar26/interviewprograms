package com.java.oops.polymorphism;

import static com.java.oops.polymorphism.MathOperations.add;

public class TestPolymorphism {

    private static final Animal animal = new Animal();
    private static final Dog dog = new Dog();
    private static final Animal anidog = new Dog(); // Child Method executes

    public static void main(String[] args) {
        int a = 10, b = 20;
        double c = 20, d = 30;
        float e = 9, f = 40;
        System.out.println(add(a, b));
        System.out.println(add(c, d));
        System.out.println(add(e, f));

        animal.sound();
        dog.sound();
        anidog.sound();

        // Usage is polymorphic but tightly coupled (Animal, Dog relationship)
        anidog.makeSound();

        // Same polymorphic usage but loosely coupled (SoundMaker, Car, Cat)
        SoundMaker[] sounds = {new Car(), new Cat()};

        for (SoundMaker sound : sounds) {
            sound.makeSound();
        }

    }
}
