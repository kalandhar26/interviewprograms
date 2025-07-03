package com.java.designPatterns.interceptingFilter;

class Target {
    public void execute(String request) {
        System.out.println("Executing request: " + request);
    }
}
