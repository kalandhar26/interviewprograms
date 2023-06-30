package com.ds.Arrays;

public class StockBuyAndSell {

    public static void main(String[] args) {

        int array[] = {3,2,6,5,0,3};

        System.out.println(maxprofit1Time(array));
    }

    public static int maximumProfit(int[] array) {
        int profit = 0;
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] < array[i + 1])
                profit = profit + (array[i + 1] - array[i]);
        }

        return profit;
    }

    public static int maxprofit1Time(int[] prices) {
        int minimum=prices[0];
        int maximum=0;

        for(int i=0;i<prices.length;i++){
            minimum = Math.min(prices[i],minimum);
            maximum = Math.max(maximum,prices[i]-minimum);
        }

        return maximum;
    }
}