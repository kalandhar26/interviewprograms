package com.java.designPatterns.builder;

class Car {
    private String engine;
    private String wheels;
    private String body;

    public void setEngine(String engine) { this.engine = engine; }
    public void setWheels(String wheels) { this.wheels = wheels; }
    public void setBody(String body) { this.body = body; }

    public void show() {
        System.out.println("Car with Engine: " + engine + ", Wheels: " + wheels + ", Body: " + body);
    }
}