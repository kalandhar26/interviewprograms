package com.ds.Arrays;

import java.util.Arrays;
import java.util.stream.IntStream;

public class MoveZerosToStart {

    public static void main(String[] args) {

        int[] array = {20, 20, 0, 0, 0, 20, 5};

        int[] zerosToStart = moveZeroToStart(array);
        int[] zerosToStartt = moveZerosToStartt(array);

        System.out.println(Arrays.toString(zerosToStart));
        System.out.println(Arrays.toString(zerosToStartt));


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

    public static int[] moveZerosToStartt(int[] array) {
        return IntStream.concat(
                Arrays.stream(array).filter(n -> n==0),
                Arrays.stream(array).filter(n -> n!=0)
        ).toArray();


    }
}
