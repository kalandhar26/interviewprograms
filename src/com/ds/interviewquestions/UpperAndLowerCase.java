package com.ds.interviewquestions;

public class UpperAndLowerCase {

    public static void main(String[] args) {
        String input = "My name is babakalandhar";
        String firstHalf="",secondHalf="";

        for(int i=0;i<input.length()/2;i++){
            firstHalf=firstHalf+input.charAt(i);
        }
        firstHalf = firstHalf.toUpperCase();
        for(int i = input.length()/2;i<input.length();i++){
            secondHalf = secondHalf+input.charAt(i);
        }
        secondHalf = secondHalf.toLowerCase();

        String result = firstHalf+secondHalf;

        System.out.println(result);

        // Call below Method

        System.out.println(upperCaseAndLowerCase("aBbAaBbA"));

    }

    public static String upperCaseAndLowerCase(String input){
        int mid = input.length()/2;
        String firstHalf="", secondHalf="";

        for(int i=0;i<input.length();i++){
            if(i<mid){
                firstHalf = firstHalf + Character.toUpperCase(input.charAt(i));
            }else{
                secondHalf += Character.toLowerCase(input.charAt(i));
            }
        }

        return firstHalf+secondHalf;
    }
}
