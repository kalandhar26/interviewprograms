package com.ds.Arrays;

public class SecondLargestElement {

    LargestElementinArray largestElementinArray = new LargestElementinArray();

    public static void main(String[] args) {
        int array[] = {10,10,10};
        System.out.println(getSecondLargestElementIndex(array));
        System.out.println(secondLargestElement1(array));

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
                if (secondlargestIndex == -1 || array[i] > array[secondlargestIndex])
                    secondlargestIndex = i;

            }
        }

        return secondlargestIndex;
    }

    public static int secondLargestElement1(int[] array) {
        int secondLargest = Integer.MIN_VALUE;
        int LargestElement = Integer.MIN_VALUE;
        int largestIndex = -1;
        int secondlargestIndex = -1;

        for (int i = 0; i < array.length; i++) {
            if (array[i] > LargestElement) {
                secondLargest = LargestElement;
                secondlargestIndex = largestIndex;
                LargestElement = array[i];
                largestIndex = i;
            } else if (array[i] > secondLargest && array[i] < LargestElement) {
                secondLargest = array[i];
                secondlargestIndex =i;
            }

        }
        System.out.println(secondlargestIndex);

        return secondLargest;
    }
}
