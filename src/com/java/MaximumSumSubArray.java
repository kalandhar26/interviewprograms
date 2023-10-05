package com.java;

import java.util.Map;
import java.util.stream.Collectors;

public class MaximumSumSubArray {

    public static void main(String[] args) {

        int[] array = {-2,1,-3,4,-1,2,1,-5,4};

        int[] result = new int[2];

        int max_sum=array[0];
        int n = array.length;
        int maximumSum = array[0];

        for(int i=1;i<n;i++){
           maximumSum = Math.max(array[i],maximumSum+array[i]);
           max_sum = Math.max(max_sum,maximumSum);
        }


        System.out.println(max_sum);

        System.out.println(reverseNumber(67897));

        int[] array1 = {10,9, 1, 5,12,2};

        System.out.println(findEquilibriumIndex(array1));

        System.out.println(checkFeasibility("abcdefgfr","abcfff"));
    }


    public static int reverseNumber(int input){

        int result =0;

        while(input!=0){
            int lastDigit = input %10;
            result = result *10+lastDigit;
            input = input /10;
        }

        return result;

    }

    public static int findEquilibriumIndex(int[] array){
        int result=-1;
        int n = array.length;
        int totalSum=0;

        for(int i =0;i<n;i++){
           totalSum += array[i];
        }

        int currentSum=0;
        for(int i =0;i<n;i++){
            if(currentSum != totalSum){
                currentSum = currentSum+array[i];
                totalSum = totalSum-array[i];
                if(currentSum==totalSum)
                    return i;
            }
        }

        return result;

    }


    public static boolean checkFeasibility(String input1, String input2){

        Map<Character, Long> map = input1.chars().mapToObj(c -> (char) c).collect(Collectors.groupingBy(c -> c, Collectors.counting()));

        int n = input2.length();

        for(int i=0;i<n;i++){
            char currentCharacter = input2.charAt(i);

            if(map.containsKey(currentCharacter) && map.get(currentCharacter)>0){
                map.put(currentCharacter,map.get(currentCharacter)-1);
            }else{
                return  false;
            }
        }
        return true;
    }
}
