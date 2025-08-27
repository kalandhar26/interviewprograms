package com.java.designPatterns.builder;

public class BuilderPatternDemo {

    public static void main(String[] args) {
        Computer computer = new Computer.ComputerBuilder("SDD","32 GB")
                .setGraphicsCardEnabled(false).build();

        computer.displaySpecification();
    }
}
