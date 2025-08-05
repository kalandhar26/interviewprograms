package com.ds.Arrays;

import java.util.Arrays;

public class ThreeSum {
    public static void main(String[] args) {
        int[] array = {3,1,7,5,11,9,15,13,10};
        int target = 30;
        int[] result = threeSum(array,target);

        for(int x : result){
            System.out.println(x);
        }
    }

    public static int[] threeSum(int[] array , int target){
        int n = array.length;
        int[] result = new int[3];
        Arrays.sort(array);
        for(int i=0;i<n-2;i++){
            for(int j=i+1;j<n-1;j++){
                for(int k=j+1;k<n;k++){
                        if(array[i]+array[j]+array[k]==target){
                            return new int[]{array[i], array[j], array[k]};
                        }
                }
            }
        }
        return new int[0];
    }

    public static int[] threeSome(int[] array, int target){
        int n = array.length;
        Arrays.sort(array);
    for(int i=0;i<n;i++){
        if(i>0 && array[i]== array[i-1]) continue;
        int left = i+1;
        int right = n-1;

        while(left<right){
            int sum = array[i] + array[left] + array[right];
            if(sum == target){
                return new int[]{array[i], array[left], array[right]};
            }else if(sum < target){
                left++;
            }else{
                right--;
            }
        }
    }
        return new int[0];
    }
}
