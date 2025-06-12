package com.ds.Arrays;

public class RemoveDigitsandNearestAlphabets {

    public static void main(String[] args) {
        System.out.println(removeNearestAphabetsandDigits("a4bgf"));
    }

    public static String removeNearestAphabetsandDigits(String input){
        char[] charArray = input.toCharArray();
        int length = charArray.length;
        StringBuilder sb = new StringBuilder();

        for(int i=0;i<length;i++){
            if(Character.isDigit(charArray[i])){
                sb.deleteCharAt(sb.length()-1);
            }else {
                sb.append(charArray[i]);
            }
        }

        return  sb.toString();
    }
}
