package com.ds.string;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class FindSubStringsAndPalindromes {

    public static void main(String[] args){
        String input = "madam";
        System.out.println(getPalindromesFromString(input));
        System.out.println(getSubstringFromString(input));
    }

    public static List<String> getPalindromesFromString(String input){
        return 
        IntStream.range(0,input.length())
        .boxed()
        .flatMap(i -> IntStream.range(i+1, input.length()+1)
            .mapToObj(j->input.substring(i,j)))
        .filter(word -> word.length() > 1 && word.contentEquals(new StringBuilder(word).reverse())).toList();
    }

    public static List<String> getSubstringFromString(String input){
        List<String> results = new ArrayList<>();

        for(int i=0;i<input.length();i++){
            for(int j=i+1;j<=input.length();j++){
                results.add(input.substring(i,j));
            }
        }
        return results;
    }
}
