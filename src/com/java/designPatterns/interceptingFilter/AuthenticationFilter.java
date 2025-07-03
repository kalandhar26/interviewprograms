package com.java.designPatterns.interceptingFilter;

class AuthenticationFilter implements Filter {
    public void execute(String request) {
        System.out.println("Authenticating request: " + request);
    }
}

