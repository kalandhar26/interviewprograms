package com.java.designPatterns.strategy;

class CreditCardStrategy implements PaymentStrategy {

    private String name;
    private String cardNumber;

    public CreditCardStrategy(String name, String cardNumber) {
        this.name = name;
        this.cardNumber = cardNumber;
    }

    public void pay(int amount) {
        System.out.println(amount + " paid with credit card");
    }
}
