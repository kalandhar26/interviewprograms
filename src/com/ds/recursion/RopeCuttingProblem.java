package com.ds.recursion;

public class RopeCuttingProblem {

    public static void main(String[] args) {

        // System.out.println(ropeCutting1(9, 2, 2, 2));

        System.out.println(ropeCutting(23, 12, 9, 11));

    }


    // wrong code
    private static int ropeCutting(int n, int a, int b, int c) {

        int small = Math.min(a, b);
        small = Math.min(small, c);

        int large = Math.max(a, b);
        large = Math.max(large, c);
        int mid = 0;

        if (large == a && small == c || large == c && small == a)
            mid = b;
        else if (large == a && small == b || large == b && small == a) {
            mid = c;
        } else if (large == b && small == c || large == c && small == b) {
            mid = a;
        }

        if (n == 0)
            return 0;
        if (n % a != 0 && n % b != 0 && n % c != 0)
            return -1;

        int maxpiece = n / small;
        int leftLength = n % small;

        if (leftLength > mid) {
            maxpiece = leftLength / mid;
            leftLength = leftLength % mid;
        }
        if (leftLength > large) {
            maxpiece = leftLength / large;
        }
        return maxpiece;

    }

    // Recursive Solution
    private static int ropeCutting1(int length, int first, int second, int third) {

        if (length == 0)
            return 0;
        if (length < 0)
            return -1;

        int result = Math.max(ropeCutting1(length - first, first, second, third), ropeCutting1(length - second, first, second, third));
        result = Math.max(result, ropeCutting1(length - third, first, second, third));

        if (result == -1)
            return -1;
        return result + 1;

    }
}
