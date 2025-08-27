package com.java.designPatterns.builder;

// Step 1 Builder Main class
public class Computer {

    private String HDD;
    private String RAM;
    private boolean isGraphicsCardEnabled;

    private Computer(ComputerBuilder builder) {
        this.HDD = builder.HDD;
        this.RAM = builder.RAM;
        this.isGraphicsCardEnabled = builder.isGraphicsCardEnabled;
    }

    public void displaySpecification() {
        System.out.println("HDD: " + HDD);
        System.out.println("RAM: " + RAM);
        System.out.println("isGraphicsCardEnabled: " + isGraphicsCardEnabled);

    }

    // Step 2 Static Buidler Class
    public static class ComputerBuilder {
        private String HDD;
        private String RAM;
        private boolean isGraphicsCardEnabled;

        // Required Parameters can go in this constructor
        public ComputerBuilder(String HDD, String RAM) {
            this.HDD = HDD;
            this.RAM = RAM;
        }

        // Optional Parameters can go in setter methods (Chained Setters)
        public ComputerBuilder setGraphicsCardEnabled(boolean isGraphicsCardEnabled) {
            this.isGraphicsCardEnabled = isGraphicsCardEnabled;
            return this;
        }

        // Write all the builder methods for all optional parameters
        public Computer build() {
            // added validations can be handled here before calling computer constructor.
            return new Computer(this);
        }
    }
}
