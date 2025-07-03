package com.java.designPatterns.builder;

class CarDirector {
    private CarBuilder builder;

    public CarDirector(CarBuilder builder) {
        this.builder = builder;
    }

    public void buildCar() {
        builder.buildEngine();
        builder.buildWheels();
        builder.buildBody();
    }

    public Car getCar() {
        return builder.getCar();
    }
}

