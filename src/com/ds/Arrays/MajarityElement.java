package com.ds.Arrays;

public class MajarityElement {

    public static void main(String[] args) {

        int[] array = {3, 2, 3};

        System.out.println(majaorityElement(array));

    }

    // My Own code
    public static int majaorityElement(int[] array) {
        int n = array.length;
        int majority = Math.abs(n / 2);

        for (int i = 0; i < n; i++) {
            int count = 0;
            int currentElement = array[i];
            for (int j = 0; j < n; j++) {
                if (currentElement == array[j]) {
                    count++;
                }
            }
            if (count > majority)
                return i;
        }

        return -1;
    }

    // Geesk for Geeks
    public static int majaorityElement1(int[] array) {

        int n = array.length;
        int majority = n / 2;

        int result = 0, count = 1;

        // to find the candidtae
        for (int i = 1; i < n; i++) {
            if (array[result] == array[i]) {
                count++;
            } else {
                count--;
            }

            if (count == 0) {
                result = i;
                count = 1;
            }
        }

        // To check the candidate is majority or not
        count = 0;
        for (int i = 0; i < n; i++) {
            if (array[result] == array[i])
                count++;
        }


        if (count <= majority)
            result = -1;

        return result;


    }
}
