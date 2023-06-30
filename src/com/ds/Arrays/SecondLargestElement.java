package com.ds.Arrays;

public class SecondLargestElement {

    LargestElementinArray largestElementinArray = new LargestElementinArray();

    public static void main(String[] args) {
        int array[] = {11, 12, 10, 65, 44};
        System.out.println(getSecondLargestElementIndex(array));

    }


    public static int secondLargestElement(int[] array) {

        int largestIndex = LargestElementinArray.getLargestIndex(array);

        int result = -1;

        for (int i = 0; i < array.length; i++) {
            if (array[i] != array[largestIndex]) {
                if (result == -1)
                    result = i;
                else if (array[i] > array[result]) {
                    result = i;
                }
            }
        }
        return result;
    }

    public static int getSecondLargestElementIndex(int[] array) {
        int secondlargestIndex = -1, largestIndex = 0;
        for (int i = 1; i < array.length; i++) {
            if (array[i] > array[largestIndex]) {
                secondlargestIndex = largestIndex;
                largestIndex = i;
            } else if (array[i] != array[largestIndex]) {
                if (secondlargestIndex == -1 || array[i] > array[largestIndex])
                    secondlargestIndex = i;

            }
        }

        return secondlargestIndex;
    }
}
