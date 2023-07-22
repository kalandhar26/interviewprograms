package com.ds.interviewquestions;

import java.util.Arrays;
import java.util.List;

public class AtosInterviewQuestions {

    public static void main(String[] args) {
        //  Merge the two Arrays
        int[] array1 = {1,2,3,4,5};
        int[] array2 = {5,6,7,8};

        int[] array3 = new int[array1.length+array2.length];
        List<int[]> list = Arrays.asList(array1, array2);
        int i=0;
        for (int[] x : list){
            for(int y : x){
                array3[i]=y;
                i++;
            }
        }

        for(int y : array3){
            System.out.println(y);
        }

    }
}
