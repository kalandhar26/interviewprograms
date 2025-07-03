package com.java.designPatterns.builder;

class SportsCarBuilder extends CarBuilder {
    public void buildEngine() { car.setEngine("V8 Engine"); }
    public void buildWheels() { car.setWheels("18 inch Wheels"); }
    public void buildBody() { car.setBody("Sports Body"); }
}
