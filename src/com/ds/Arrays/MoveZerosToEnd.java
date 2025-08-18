package com.ds.Arrays;

import java.util.Arrays;
import java.util.stream.IntStream;

public class MoveZerosToEnd {

    public static void main(String[] args) {

        int[] array = {1,2,0,0,3,4,0,5,6,0,7};

        int[] zerosToEnd = moveZerosToEnd(array);
        int[] zerosToEndd = moveZerosToEndd(array);

        System.out.println(Arrays.toString(zerosToEnd));
        System.out.println(Arrays.toString(zerosToEndd));
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

    public static int[] moveZerosToEndd(int[] array) {
        return IntStream.concat(
                Arrays.stream(array).filter(n -> n!=0),
                Arrays.stream(array).filter(n -> n==0)
        ).toArray();


    }

}
