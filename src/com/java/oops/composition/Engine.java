package com.java.oops.composition;

public class Engine {
    // Part of Car (cannot exist independently)

    @Override
    public String toString() {
        return "Engine#" + hashCode();
    }
}
