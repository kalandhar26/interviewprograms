package com.ds.interviewquestions;

public class PrintStarts {

    public static void main(String[] args) {
        // 1 start in first row 2 starts in 2nd row like that 5 stars in 5 rows

        int x = 5;

        for (int i = 0; i < x; i++) {
            // to handle spaces
            for (int k = x - i; k > 1; k--) {
                System.out.print(" ");
            }
            // to print stars
            for (int j = 0; j <= i; j++) {
                System.out.print("* ");
            }
            // move to next line
            System.out.println();
        }
    }
}
