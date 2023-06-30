package com.ds.interviewquestions;

public class RemoveOccurances {

    public static void main(String[] args) {

        System.out.println(removeOcuurances('c',"cloudtech"));

    }

    public static String removeOcuurances(Character ch,String str){
        String result="";
        for(int i=0;i<str.length();i++){
            if(str.charAt(i)==ch){
               continue;
            }else{
                result = result+str.charAt(i);
            }
        }

        return result;
    }
}
