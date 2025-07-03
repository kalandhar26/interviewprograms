package com.java.oops.composition;

import java.util.List;

public class Classroom {
    private final Student student; // Aggregation - Classroom uses Student

    public Classroom(Student student) {
        this.student = student; // Student passed to Classroom
        System.out.println("Classroom created with " + student);
    }

    @Override
    public String toString() {
        return "Classroom with " + student;
    }
}
