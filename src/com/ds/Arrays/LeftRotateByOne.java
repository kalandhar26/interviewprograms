package com.ds.Arrays;

public class LeftRotateByOne {

    public static void main(String[] args) {

        int array[] = new int[] {30, 5, 20};

        //  int[] reverserArray = leftRotate(array);

        int[] leftRotateByNplaces = leftRotate(array, 2);

      /*  for (int x : reverserArray)
            System.out.print(x + " ");*/

        for (int x : leftRotateByNplaces)
            System.out.print(x + " ");

    }

    public static int[] leftRotate(int[] array) {

        int temp = array[0];
        for (int i = 0; i < array.length - 1; i++) {
            array[i] = array[i + 1];
        }

        array[array.length - 1] = temp;

        return array;
    }

    //Naive Solution
    public static int[] leftRotateByNPlaces(int[] array, int n) {

        for (int i = 1; i <= n; i++) {
            array = leftRotate(array);
        }

        return array;
    }

    // Efficient Solution
    public static int[] leftRotateByNplaces1(int[] array, int d) {
        int n = array.length;
        int[] temp = new int[d];
        for (int i = 0; i < d; i++) {
            temp[i] = array[i];
        }
        for (int i = d; i < n; i++) {
            array[i - d] = array[i];
        }
        for (int i = 0; i < d; i++) {
            array[n - d + i] = temp[i];
        }
        return array;
    }

    // Best Efficient Solution

    public static int[] leftRotate(int[] array, int d) {
        int size = array.length;
        array = reverse(array, 0, d - 1);
        array = reverse(array, d, size - 1);
        array = reverse(array, 0, size - 1);

        return array;
    }

    public static int[] reverse(int[] array, int low, int high) {
        while (low < high) {
            int temp = array[low];
            array[low] = array[high];
            array[high] = temp;
            low++;
            high--;
        }
        return array;
    }
}
