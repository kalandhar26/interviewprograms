package com.ds.interviewquestions;

public class EvenOrOddNumber {

    public static void main(String[] args) {

        int number = 100;
        if ((number & 1) == 0) {
            System.out.println(number + " is Even");
        } else {
            System.out.println(number + " is Odd");
        }

        evenorOdd(number);
    }

    public static void evenorOdd(int number){
        int quo = number/2;
        if(quo*2==number){
            System.out.println(number+" is E1");
        }else {
            System.out.println(number+" is Ado");
        }
    }
}
