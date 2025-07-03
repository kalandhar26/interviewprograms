package com.java.oops.composition;

public class Car {
    private final Engine engine; // Composition - Car owns Engine

    public Car() {
        this.engine = new Engine(); // Engine created with Car
        System.out.println("Car created with " + engine);
    }

    public Engine getEngine() {
        return engine;
    }

    @Override
    public String toString() {
        return "Car with " + engine;
    }
}