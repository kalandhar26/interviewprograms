package com.ds.Arrays;

import java.util.*;

public class NonAdjacentMaxSum {
 public static void main(String[] args){

    int[] array = {8,6,2,5,1,4,3};
    System.out.println(nonAdjMaxSum(array));

 }

 public static int nonAdjMaxSum(int[] array){
    if(array.length == 0) return 0;
    if(array.length == 1) return array[0];

    int prev1 = Math.max(array[0], array[1]);
    int prev2 = array[0];

    for(int i = 2; i < array.length; i++){
        int current = Math.max(prev1, prev2 + array[i]);
        prev2 = prev1;
        prev1 = current;
    }
    return prev1;
 }
}
