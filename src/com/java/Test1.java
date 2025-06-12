package com.java;

import java.util.stream.Collectors;

public class Test1 {

    public static void main(String[] args) {

        String input = "java is a Programming Language";

        String[] result = input.split(" ");

        for(String x : result){

            System.out.println("=============");
        }

        int[] array = {2,4,6,3,6,3,3,7,4,2,6,9,2,2,2};

        findEquilibrium(array);
    }


    public static void findEquilibrium(int[] array){
        int n = array.length;
        int totalSum=0;
        for(int i =0;i<n;i++){
            totalSum += array[i];
        }

        int currentSum =0;
        for(int i=0;i<n;i++){
            totalSum = totalSum - array[i];
            if(currentSum==totalSum){
                System.out.println(i);
            }else{
                currentSum += array[i];
            }
        }

        System.out.println("-1");
    }
}
