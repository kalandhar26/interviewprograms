package com.java.oops.abstrat;

abstract class Shape {

    private String color;

    public Shape() {
        this("Black"); // Default Colour or we can write common code that can be used in all subclasses
        System.out.println("Shape No Arg constructor called");
    }

    public Shape(String color) {
        this.color = color;
        System.out.println("Shape Args constructor called");
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }


    abstract double area();

    abstract double perimeter();
}
