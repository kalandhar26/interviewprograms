package com.ds.interviewquestions;

import java.util.ArrayList;
import java.util.List;

public final class ImmutableClass {

    //1.  Declare the class as final, to prevent extending(inheriance) or modifying its behavior.

    //2. Declare all fields as final , they will unmodified once assigned, It will ensure the state of object cannot be changed
    private final int immutableField;
    //3. Do not provide setters for the fields. Provide only getters

    private int getImmutableField() {
        return immutableField;
    }

    //4. Make Constrctor private, this will restrict creation of instances within the class. External code cannot create instances.
    private ImmutableClass(int immutableField) {
        this.immutableField = immutableField;
    }

    //5. If class has mutable reference fields, do not directly expose or return them. Make defensive  copies then return them.
    private  List<String> list;

    private ImmutableClass(int immutableField, List<String> list1) {
        this.immutableField = immutableField;
        this.list = new ArrayList<>(list1);
    }

    public List<String> getMutableList() {
        return new ArrayList<>(list);
    }


}
