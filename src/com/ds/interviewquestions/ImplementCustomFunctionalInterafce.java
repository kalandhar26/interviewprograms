package com.ds.interviewquestions;

public class ImplementCustomFunctionalInterafce {

    public static void main(String[] args) {

        // Using a lambda expression to implement the functional interface
        CustomFunctionalInterface cfi1 = (a,b)-> a+b;

        // Using a method reference to implement the functional interface
        CustomFunctionalInterface cfi2 = Integer::sum;

        // Calling the methods defined in the functional interfaces
        int sum = cfi1.sum(10, 20);
        int sum1 = cfi2.sum(30, 40);

        System.out.println(sum1+" "+sum);

    }
}
