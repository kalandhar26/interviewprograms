package com.ds.introduction;

public final class Employee {

    private final String name;

    private final double age;

    public Employee(String name, double age) {
        this.name = name;
        this.age = age;
    }

    public Employee(double age, String name) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public double getAge() {
        return age;
    }
}
