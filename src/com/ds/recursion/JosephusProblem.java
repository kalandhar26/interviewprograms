package com.ds.recursion;

public class JosephusProblem {

    public static void main(String[] args) {
        System.out.println(josephusProblem(8,3));
    }

    private static int josephusProblem(int n, int k) {

        if (n == 1)
            return 0;
        else
        return (josephusProblem(n - 1, k)+k)%n;

    }
}
