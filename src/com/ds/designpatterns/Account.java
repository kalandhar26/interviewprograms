package com.ds.designpatterns;


/*
create objects based on certain conditions or configurations,
such as different types of accounts in the Account Management module.

We used the Factory Method pattern to create instances of objects with
 different behaviors, depending on certain conditions or input.
 */
interface Account {
    void deposit(double amount);
    void withdraw(double amount);
}

class SavingsAccount implements Account {
    @Override
    public void deposit(double amount) {

    }

    @Override
    public void withdraw(double amount) {

    }
    // Implementation of SavingsAccount
}

class CheckingAccount implements Account {
    @Override
    public void deposit(double amount) {

    }

    @Override
    public void withdraw(double amount) {

    }
    // Implementation of CheckingAccount
}

interface AccountFactory {
    Account createAccount();
}

class SavingsAccountFactory implements AccountFactory {
    public Account createAccount() {
        return new SavingsAccount();
    }
}

class CheckingAccountFactory implements AccountFactory {
    public Account createAccount() {
        return new CheckingAccount();
    }
}

