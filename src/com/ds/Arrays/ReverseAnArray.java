package com.ds.Arrays;

public class ReverseAnArray {

    public static void main(String[] args) {

        int array[] = {11, 12, 10, 65, 44};

        int[] reverserArray = reverseAnArray(array);

        for (int x : reverserArray)
            System.out.print(x + " ");

    }

    public static int[] reverseAnArray(int[] array) {
        int length = array.length - 1;
        for (int i = 0; i < length; i++) {
            int temp = array[i];
            array[i] = array[length];
            array[length] = temp;
            length--;
        }
        return array;
    }
}
