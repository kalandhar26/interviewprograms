package com.ds.Arrays;

public class InsertAtStart {

    public static void main(String[] args) {
        int[] numbers = new int[10];
        numbers[1] = 1;
        numbers[0] = 0;
        numbers[2] = 2;
        numbers[3] = 3;
        numbers[4] = 5;

        System.out.println("==========Before Inserting===============");
        for (int x : numbers)
            System.out.println(x);
        System.out.println("==========After Inserting===============");
        int[] ints = addAtStart(numbers, 5, 10, 0, 4);
        for (int x : ints)
            System.out.println(x);

    }

    private static int[] addAtStart(int[] array, int size, int capacity, int position, int newElement) {

        int index = 0; // This line Start
        if (size == capacity)
            return array;
        if (position > 0) // This line Start
            index = position - 1;

        for (int i = size - 1; i >= index; i--)
            array[i + 1] = array[i];
        array[index] = newElement;

        return array;
    }
}
