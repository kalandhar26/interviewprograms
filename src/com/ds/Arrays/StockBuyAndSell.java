package com.ds.Arrays;

public class StockBuyAndSell {

    public static void main(String[] args) {

        int array[] = {3,2,6,5,0,3};

        System.out.println(maximumProfit(array));
        System.out.println(maxProfitByBuyingAndSellingOnce(array));
    }

    public static int maximumProfit(int[] array) {
        int profit = 0;
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] < array[i + 1])
                profit = profit + (array[i + 1] - array[i]);
        }

        return profit;
    }

    public static int maxProfitByBuyingAndSellingOnce(int[] prices) {
        int minimumPrice=prices[0];
        int maximumProfit=0;

        for(int i=0;i<prices.length;i++){
            minimumPrice = Math.min(prices[i],minimumPrice);
            maximumProfit = Math.max(maximumProfit,prices[i]-minimumPrice);
        }

        return maximumProfit;
    }
}