package com.java.designPatterns.strategy;

class PayPalStrategy implements PaymentStrategy {
    private String email;

    public PayPalStrategy(String email) {
        this.email = email;
    }

    public void pay(int amount) {
        System.out.println(amount + " paid using PayPal");
    }
}
