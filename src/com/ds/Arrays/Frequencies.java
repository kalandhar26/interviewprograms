package com.ds.Arrays;

public class Frequencies {

    public static void main(String[] args) {

        int array[] = {10,20,30,10,40,40};

        frequencies(array);

    }

    public static void frequencies(int[] array) {

        for (int i = 0; i < array.length; i++) {
            int count = 1;
            for (int j = 0; j < array.length; j++) {
                if (i != j && array[i] == array[j]) {
                    count++;
                }
            }
            System.out.println(array[i] + " " + count + " times");
        }
    }
}
