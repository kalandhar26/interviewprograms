package com.ds.interviewquestions;

import javax.sound.midi.Soundbank;

public class FindContinuosNumbersinArray {

    public static void main(String[] args) {

        int[] arr = {1,2,3,4,5,9,7,4,10,11,12,13,20,21,22,23,29,25};

        int length = arr.length;

        for(int i=1;i<length;i++){
            if(arr[i] == arr[i-1]+1){
                System.out.print(arr[i-1]+" ");
            }else if (arr[i] != arr[i-1]+1 && arr[i-1] == arr[i-2]+1 ) {
                System.out.print(arr[i-1]+" ");
                System.out.println();
            } 
        }
    }
}
