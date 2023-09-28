package com.ds.interviewquestions;

import com.java.ChildClass;

import java.io.FileNotFoundException;
import java.io.IOException;

public class MainClass {

    public static void main(String[] args) throws FileNotFoundException {
//        ParentClass p = new ParentClass();
//        p.readFile();
//        ParentClass c = new ChildClass();
//        c.readFile();
        ChildClass cd = new ChildClass();
        cd.readFile();

    }
}
