package com.ds.introduction;

public class Test {

    public static void main(String[] args) {

        String[] strings = fizzBuzz(15);

        for (String x : strings)
            System.out.println(x);

    }


    public static String[] fizzBuzz(Integer n) {

        String[] result = new String[n];

        for (Integer i = 1; i <=n; i++) {
             if (i % 3 == 0 && i % 5 == 0) {
                result[i-1] = "FizzBuzz";
            } else if (i % 3 == 0) {
                result[i-1] = "Fizz";
            } else if (i % 5 == 0) {
                result[i-1] = "Buzz";
            } else {
                result[i-1] = i.toString();
            }
        }
        return result;
    }
}
