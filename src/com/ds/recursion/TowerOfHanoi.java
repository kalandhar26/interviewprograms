package com.ds.recursion;

public class TowerOfHanoi {

    public static void main(String[] args) {

        TOH(4, 'X', 'Y', 'Z');

    }

    private static int TOH(int n, char A, char B, char C) {
        int count = 0;
        if (n == 1) {
            System.out.println(" move " + n + " from " + A + " to " + C);
            count++;
            return count;
        }

        TOH(n - 1, A, C, B);
        System.out.println(" move " + n + " from " + A + " to " + C);
        TOH(n - 1, B, A, C);

        return count;

    }
}
