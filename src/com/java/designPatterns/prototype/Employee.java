package com.java.designPatterns.prototype;

public class Employee implements Prototype {

    private String name;
    private String role;

    public Employee(String name, String role) {
        this.name = name;
        this.role = role;
    }

    @Override
    public Prototype clone() {
        return new Employee(name, role);
    }

    @Override
    public String toString() {
        return "Employee [name=" + name + ", role=" + role + "]";
    }
}
