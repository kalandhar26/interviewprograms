package com.ds.interviewquestions;

public class RemoveDigits_IBM {

    public static void main(String[] args) {
        String input ="ba12ba34kala45ndh67ar89";

        // 1st way
        String output = input.replaceAll("\\d","");
        System.out.println(output);

        String output1="";

        // 2nd way
        for(int i=0;i<input.length();i++){
            Character c = input.charAt(i);
            if(!Character.isDigit(c)){
                output1 += c;
            }
        }
        System.out.println(output1);
    }
}
