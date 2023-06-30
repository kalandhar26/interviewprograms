package com.ds.Arrays;

public class DeletionArray {

    public static void main(String[] args) {

        int array[]={1,2,3,5,6};

        array = deleteElement(array,5,3);

        for(int x : array)
            System.out.println(x);

    }

    private static int[] deleteElement(int[] array, int size, int elementTobeDeleted) {

        int lengthstart = array.length;
        System.out.println(lengthstart);
        int i;
        // This loop will traverse till it found the elemnet to be deleted if found the exit from the loop
        for (i = 0; i < size; i++) {
            if (array[i] == elementTobeDeleted)
                break;
        }

        // This condition check if i is actually the size of array it means element to be deleted Not Found Hence returninf the same array.
        if (i == size)
            return array;

        // This loop will start from where the first loop stoipped and We are placing all elements in right side.
        for (int j = i; j < size - 1; j++) {
            array[j] = array[j + 1];
        }

        int lengthend = array.length;
        System.out.println(lengthend);

        return array;

    }
}
