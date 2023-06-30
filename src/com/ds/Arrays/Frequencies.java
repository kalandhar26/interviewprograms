package com.ds.Arrays;

public class Frequencies {

    public static void main(String[] args) {

        int array[] = {10,20,30,40,40};

        frequencies(array);

    }

    public static void frequencies(int[] array) {

        for (int i = 0; i < array.length; i++) {
            int count = 1;
            for (int j = i + 1; j < array.length; j++) {
                if (array[i] == array[j]) {
                    count++;
                    i++;
                }
            }
            System.out.println(array[i] + " " + count + " times");
        }
    }
}
