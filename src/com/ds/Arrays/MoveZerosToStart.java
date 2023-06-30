package com.ds.Arrays;

public class MoveZerosToStart {

    public static void main(String[] args) {

        int array[] = {20, 20, 0, 0, 0, 20, 5};

        int[] zerosToEnd = moveZeroToStart(array);

        for (int x : zerosToEnd)
            System.out.print(x + " ");

    }

    public static int[] moveZeroToStart(int[] array) {
        int count = array.length - 1;
        for (int i = count; i >= 0; i--) {
            if (array[i] != 0) {
                array[count] = array[i];
                count--;
            }
        }

        while (count >= 0) {
            array[count] = 0;
            count--;
        }

        return array;
    }
}
