package com.ds.interviewquestions;

public class SingletonClass {

    // Create a instance of Singleton Class Type
    private static SingletonClass instance;
    // This will hold the single instance of the Class

    // Create private Constructor of  the Class
    private SingletonClass() {
    }
    // Constructor is private because this prevents direct instantiation of the class from outside.


    // Now we need to create a method to allow external code to access the instance of the class

    public static SingletonClass getInstance() {
        if (instance == null) {
            synchronized (SingletonClass.class) {
                // Checking Locking 2 times ( before and after synchronization to ensure thread safety
                if (instance == null)
                    // lazily initialize the varibale ( we are not craeted upfront during variable declaration)
                    instance = new SingletonClass();
            }
        }
        // We are returning the instance
        return instance;
    }
}
