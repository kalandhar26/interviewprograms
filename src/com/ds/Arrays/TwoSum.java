package com.ds.Arrays;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class TwoSum {

    public static void main(String[] args) {

        int[] array = {3,3};
        int target = 6;

        int[] result = twoSum(array,target);

        for(int x : result){
            System.out.println(x);
        }

      Arrays.stream(array).boxed().collect(Collectors.groupingBy(n->n,Collectors.counting())).entrySet().stream().filter(entry -> entry.getValue()==1).map(Map.Entry::getKey).mapToInt(Integer::valueOf).forEach(System.out::println);
    }

    // Normal Solution
    public static int[] twoSum(int[] nums, int target){
        int[] resultArray = new int[2];
        resultArray[0]=resultArray[1]=-1;
        for(int i =0;i<nums.length;i++){
            for(int j=i+1;j<nums.length;j++){
                if(nums[i]+nums[j]==target){
                    resultArray[0]=i;
                    resultArray[1]=j;
                    return resultArray;
                }
            }
        }
        return  resultArray;
    }

    // Enhanced Solution

    public  static  int[] twoSum1(int[] nums, int target){
        int[] resultArray = new int[2];
        resultArray[0]=resultArray[1]=-1;
        Map<Integer, Integer> map = new HashMap<>();

        for(int i=0;i< nums.length;i++){
            int x = target-nums[i];
            if(map.containsKey(x)){
                map.put(x,i);
            }
        }

        return resultArray;
    }
}
