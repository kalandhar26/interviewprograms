package com.ds.Arrays;

public class JumpingGame {

    public static void main(String[] args) {
        int[] array = {5,1,1,1,1};
        System.out.println(jumpGame(array));
        System.out.println(jumpGamee(array));
    }

    public static boolean jumpGame(int[] array) {
        int n = array.length;
        int finalIndex = n - 1;
        int currentIndex = 0;

        for (int i = 0; i < n; i = currentIndex) {
            currentIndex = currentIndex + array[i];
            if (currentIndex == finalIndex) {
                return true;
            }
        }
        return false;
    }

    public static boolean jumpGamee(int[] nums) {
        int reachable = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i > reachable) return false;  // Can't progress further
            reachable = Math.max(reachable, i + nums[i]);
            if (reachable == nums.length - 1) return true;  // Reached end
        }
        return false;
    }
}
