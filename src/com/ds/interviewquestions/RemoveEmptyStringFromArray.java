package com.ds.interviewquestions;

import java.util.Arrays;

public class RemoveEmptyStringFromArray {

    public static void main(String[] args) {
        String[] array = {"abc","def","edf","","12","rf",""};

        String[] array1 = Arrays.stream(array).filter(s -> s.length() > 0).toArray(String[]::new);

        for(String obj : array1){
            System.out.println(obj);
        }
    }
}
