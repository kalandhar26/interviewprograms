package com.ds.Arrays;

import java.util.Arrays;

public class ProductOfAllElementsExceptSelf {

    public static void main(String[] args) {

        int[] array = {0,0,1,0,0};

        int[] productResult = productExceptSelf(array);
        int[] sumResult = sumExceptSelf(array);
        System.out.println(Arrays.toString(productResult)+" || "+Arrays.toString(sumResult));

    }

    public static int[] productExceptSelf(int[] array){
        int n = array.length;
        int[] result = new int[n];

        // Initialize the result array with 1
        Arrays.fill(result, 1);

        int leftProduct=1;

        for(int i=0;i<n;i++){
            result[i] *= leftProduct;
            leftProduct *= array[i];
        }

        int rightProduct=1;
        for(int i= n-1;i>=0;i--){
            result[i] *= rightProduct;
            rightProduct *= array[i];
        }

        return result;
    }


    public static int[] sumExceptSelf(int[] array){
        int n = array.length;
        int[] result = new int[n];

        int leftSum=0;
        for(int i=0;i<n;i++){
            result[i] += leftSum;
            leftSum += array[i];
        }

        int rightSum =0;
        for(int i = n-1;i>=0;i--){
            result[i] += rightSum;
            rightSum += array[i];
        }

        return result;
    }
}
