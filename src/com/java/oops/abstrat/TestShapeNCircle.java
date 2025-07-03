package com.java.oops.abstrat;

public class TestShapeNCircle {

    public static void main(String[] args) {
        Shape shape = new Circle(10);
        System.out.println(shape.getColor()); // Black
        System.out.println(shape.area());
        System.out.println(shape.perimeter());

        Circle circle = new Circle(20);
        System.out.println(circle.getColor()); // Black
        System.out.println(circle.area());
        System.out.println(circle.perimeter());

        Shape shape1 = new Circle(8,"Green");
        System.out.println(shape1.getColor()); // Black
        System.out.println(shape1.area());
        System.out.println(shape1.perimeter());

        Circle circle1 = new Circle(12,"Yellow");
        System.out.println(circle1.getColor()); // Black
        System.out.println(circle1.area());
        System.out.println(circle1.perimeter());

    }


}
