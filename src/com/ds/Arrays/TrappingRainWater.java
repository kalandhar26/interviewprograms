package com.ds.Arrays;

public class TrappingRainWater {

    public static void main(String[] args) {

        int[] array = {3, 2, 1, 6, 4, 5};

        System.out.println(trapRainWater(array));
        System.out.println(trapWater(array));
        System.out.println(waterTrapped(array));
    }

    public static int trapRainWater(int[] array) {
        int n = array.length, result = 0;

        for (int i = 1; i < n - 1; i++) {
            int leftMax = array[i];
            for (int j = 0; j < i; j++) {
                leftMax = Math.max(leftMax, array[j]);
            }
            int rightMax = array[i];
            for (int j = i + 1; j < n; j++) {
                rightMax = Math.max(rightMax, array[j]);
            }

            result = result + (Math.min(leftMax, rightMax) - array[i]);
        }
        return result;
    }


    public static int trapWater(int[] array) {
        int empty = 0;
        int length = array.length;
        int[] leftMaxi = new int[length];
        int[] rightMaxi = new int[length];

        // This loop will save the Maximum values
        leftMaxi[0] = array[0];
        for (int i = 1; i < length; i++) {
            leftMaxi[i] = Math.max(array[i], leftMaxi[i - 1]);
        }
        // This loops will save the Maximum values from last
        rightMaxi[length - 1] = array[length - 1];
        for (int i = length - 2; i >= 0; i--) {
            rightMaxi[i] = Math.max(array[i], rightMaxi[i + 1]);
        }

        for (int i = 0; i < length; i++) {
            empty = empty + (Math.min(leftMaxi[i], rightMaxi[i]) - array[i]);
        }

        return empty;
    }


    public static int waterTrapped(int[] array){
        int left=0;
        int right = array.length-1;
        int leftMax = array[left];
        int rightMax = array[right];
        int waterTrapped=0;
        while(left < right){
            if(leftMax <= rightMax){
                left++;
                leftMax = Math.max(leftMax,array[left]);
                waterTrapped += leftMax-array[left];
            }else {
                right--;
                rightMax = Math.max(rightMax,array[right]);
                waterTrapped += rightMax-array[right];
            }
        }

        return waterTrapped;
    }
}
