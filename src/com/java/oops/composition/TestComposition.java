package com.java.oops.composition;

public class TestComposition {
    public static void main(String[] args) {
        // 1. Composition Example (Car-Engine)
        Car car = new Car();
        Engine engine = car.getEngine();
        System.out.println("[1] " + car);
        System.out.println("[2] " + engine);

        // 2. Aggregation Example (Classroom-Student)
        Student student = new Student();
        Classroom classroom = new Classroom(student);
        System.out.println("[3] " + classroom);
        System.out.println("[4] " + student);

        // 3. Destroying container objects
        System.out.println("\nSetting references to null...");
        car = null;       // Destroy Car (composition)
        classroom = null; // Destroy Classroom (aggregation)

        // 4. Force garbage collection (just for demonstration)
        System.gc();

        // 5. Check if contained objects still exist
        try {
            Thread.sleep(5000); // Give GC time to work
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\nAfter garbage collection:");
        System.out.println("[5] Engine: " + engine); // May still exist (not guaranteed)
        System.out.println("[6] Student: " + student); // Will still exist
    }
}
