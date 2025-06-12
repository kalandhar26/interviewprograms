package com.ds.Arrays;

public class MoveZerosToEnd {

    public static void main(String[] args) {

        int array[] = {1,2,3,4,5,6,7};

        int[] zerosToEnd = moveZerosToEnd(array);

        for(int x: zerosToEnd)
            System.out.print(x+" ");
    }

    public static int[] moveZerosToEnd(int[] array) {

        int count = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] != 0) {
                array[count] = array[i];
                count++;
            }
        }

        while (count < array.length) {
            array[count] = 0;
            count++;
        }

        return array;
    }

}
