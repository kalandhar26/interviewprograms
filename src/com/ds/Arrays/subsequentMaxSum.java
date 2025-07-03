package com.ds.Arrays;

public class subsequentMaxSum {

    public static void main(String[] args) {
        int[] array = {-1,-2,3,4};
        int k =3;
        for(int x : subsequentMaxSum(array,k)){
            System.out.println(x);
        }
    }

    public static int[] subsequentMaxSum(int[] array, int k){
        int  maxSum=0,currentSum=0, startIndex=0;

        for(int i=0;i<k;i++){
            currentSum += array[i];
        }

        maxSum = currentSum;

        for(int i =k;i<array.length;i++){
            currentSum = currentSum - array[i-k]+array[i];
            if(currentSum > maxSum){
                maxSum = currentSum;
                startIndex = i-k+1;
            }
        }

        int[] result = new int[k];
        System.arraycopy(array,startIndex,result,0,k);
        return result;
    }
}
