package com.ds.Arrays;

import java.util.Arrays;

public class KoKoEatBananas {

    public static void main(String[] args) {
        int[] array = {5, 10, 3};
        int hours = 4;
        System.out.println(minEatingSpeed(array, hours));
    }

    public static int minEatingSpeed(int[] array, int hours) {
        int left = 1;
        int right = Arrays.stream(array).max().orElseThrow(() -> new IllegalArgumentException("Piles array cannot be empty"));

        int result = right;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            long hoursNeeded = calculateHours(array, mid);
            if (hoursNeeded <= hours) {
                result = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return result;
    }

    private static long calculateHours(int[] piles, int k) {
        long hours = 0;
        for (int pile : piles) {
            hours += pile / k;
            if (pile % k != 0) {
                hours++;
            }
        }
        return hours;
    }
}
