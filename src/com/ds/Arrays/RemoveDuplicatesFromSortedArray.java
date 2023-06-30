package com.ds.Arrays;

public class RemoveDuplicatesFromSortedArray {

    public static void main(String[] args) {

        int[] array = {1,2, 3, 4, 4, 5};

        int[] removeDuplicates = removeDuplicates(array);

        for (int x : removeDuplicates)
            System.out.println("Array" + x);

    }

    public static int[] removeDuplicates(int[] array) {

        int result=1, length=array.length;

        for(int i=1;i<length;i++){
            if(array[i] != array[result-1]){
                array[result]=array[i];
                result++;
            }
        }

        System.out.println(result);
        return array;
    }

}
