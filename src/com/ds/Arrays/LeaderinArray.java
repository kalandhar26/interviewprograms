package com.ds.Arrays;

public class LeaderinArray {

    public static void main(String[] args) {
        int array[] = new int[]{7, 10, 4, 10, 6, 5, 2};
        leaderElement1(array);
    }

    // Naive Solution
    public static void leaderinArray(int[] array) {

        for (int i = 0; i < array.length; i++) {
            boolean flag = false;
            for (int j = i + 1; j < array.length; j++) {
                if (array[i] <= array[j]) {
                    flag = true;
                    break;
                }
            }
            if (flag == false)
                System.out.println(array[i]);
        }

    }

    // Efficient Solution
    public static void leaderElement1(int[] array) {
        int currentLeader = array[array.length - 1];
        System.out.println(currentLeader);
        for (int i = array.length - 2; i >= 0; i--) {
            if (currentLeader < array[i]) {
                currentLeader = array[i];
                System.out.println(currentLeader);
            }
        }
    }
}
