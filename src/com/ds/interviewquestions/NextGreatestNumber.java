package com.ds.interviewquestions;

public class NextGreatestNumber {

    public static void main(String[] args) {

    }

    public static int getNextGreatestNumber(int number){
        char[] digits = Integer.toString(number).toCharArray();
        int n = digits.length;
        int i,j;

        for(i=n-2; i>=0; i--){
            if(digits[i]<digits[i+1]){
                break;
            }
        }

        if(i<0)
            return -1;

        return 0;


    }
}
