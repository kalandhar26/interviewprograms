package com.java.oops.abstrat;

public class Circle extends Shape {

    private double radius;

    public Circle(double radius) {
        super();  // Calls Shape's no-arg constructor
        this.radius = radius;
    }

    public Circle(double radius, String color) {
        super(color);  // Calls Shape's no-arg constructor
        this.radius = radius;
    }

    @Override
    double area() {
        return Math.PI * radius * radius;
    }

    @Override
    double perimeter() {
        return 2 * Math.PI * radius;
    }
}
