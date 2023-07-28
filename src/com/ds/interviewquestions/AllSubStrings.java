package com.ds.interviewquestions;

import java.util.ArrayList;
import java.util.List;

public class AllSubStrings {

    public static void main(String[] args) {
        String input = "BabaKalandhar";
        int n = input.length();
        List<String> subStrings = new ArrayList<>();

        for(int i=0;i<n;i++){
            for(int j = i+1;j<=n;j++){
                subStrings.add(input.substring(i,j));
            }
        }

      //  subStrings.forEach(System.out::println);

        // Another Way to find SubStrings
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= n - i; j++) {
                int m = j + i - 1;
                for (int k = i; k <= m; k++) {
                    System.out.print(input.charAt(k));
                }
                System.out.println();
            }
        }
    }
}
