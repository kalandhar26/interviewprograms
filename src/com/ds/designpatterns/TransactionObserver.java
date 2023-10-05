package com.ds.designpatterns;

import java.util.ArrayList;
import java.util.List;

/*
When you need to implement event-driven behavior, such as notifying interested parties
when a transaction is made in the Payments and Transfers module.
 */
interface TransactionObserver {
    void update(Transaction transaction);
}

class Transaction {
    // Transaction details

    private List<TransactionObserver> observers = new ArrayList<>();

    public void addObserver(TransactionObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(TransactionObserver observer) {
        observers.remove(observer);
    }

    public void execute() {
        // Execute the transaction
        // Notify observers
        for (TransactionObserver observer : observers) {
            observer.update(this);
        }
    }
}

class EmailNotificationObserver implements TransactionObserver {
    @Override
    public void update(Transaction transaction) {
        // Send email notification
    }
}

