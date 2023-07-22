package com.ds.interviewquestions;

public class RemoveOccurances {

    public static void main(String[] args) {

        System.out.println(removeOccurrences('c',"cloudtech"));

    }

    public static String removeOccurrences(Character ch, String str){
        StringBuilder result= new StringBuilder();
        for(int i=0;i<str.length();i++){
            if(str.charAt(i)==ch){
               continue;
            }else{
                result.append(str.charAt(i));
            }
        }

        return result.toString();
    }
}
