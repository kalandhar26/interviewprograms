package com.java;

import java.io.FileNotFoundException;

public class ChildClass extends ParentClass {
    void readFile() throws FileNotFoundException {
        // Some code that throws IOException
        System.out.println("ChildClass");
    }
}
