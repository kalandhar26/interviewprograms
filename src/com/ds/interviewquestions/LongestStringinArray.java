package com.ds.interviewquestions;

import java.util.Arrays;
import java.util.Optional;

public class LongestStringinArray {



    public static void main(String[] args) {
        String[] array = {"Baba","Sharin","Anjum","Kalandhar","Nikhil"};
        String result="";
        for(int i=0;i< array.length;i++){
            if(result.length()<array[i].length()){
                result=array[i];
            }
        }

        System.out.println(result);

        String result2 = Arrays.stream(array).reduce((word1, word2) -> (word1.length() > word2.length() ? word1 : word2)).get();
        System.out.println(result2);
    }
}
