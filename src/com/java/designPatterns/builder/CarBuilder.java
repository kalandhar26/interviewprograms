package com.java.designPatterns.builder;

abstract class CarBuilder {
    protected Car car = new Car();

    public abstract void buildEngine();
    public abstract void buildWheels();
    public abstract void buildBody();
    public Car getCar() { return car; }
}