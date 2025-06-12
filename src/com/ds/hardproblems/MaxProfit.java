package com.ds.hardproblems;

public class MaxProfit {

    public static void main(String[] args) {
        int[] array = {1,2,3,4,5,6,2,8};
        System.out.println(maxProfit(array));

    }

    public static int maxProfit(int[] array){
        int maxProfit=0;
        for(int i =1;i<array.length;i++){
            if(array[i-1]<array[i]){
              maxProfit = maxProfit + (array[i]-array[i-1]);
            }
        }

        return maxProfit;

    }
}
