package com.java;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ChildClass extends ParentClass{
    void readFile() throws FileNotFoundException {
        // Some code that throws IOException
        System.out.println("ChildClass");
    }
}