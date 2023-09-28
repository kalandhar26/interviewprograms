package com.ds.interviewquestions;

public class MultiplyWithOtherElements {

    public static void main(String[] args) {

        int[] array ={10, 3, 5, 6, 2};

        int[] result=getMultiple1(array);

        for(int x:result)
            System.out.println(x);

    }

    public static int[] getMultiple(int[] array){

        int n = array.length;
        int[] result = new int[n];
        int count=0;
        for(int i=0;i<n;i++){
            int x =1;
            for(int j=0;j<n;j++){
                if(i!=j){
                    x = x*array[j];
                }
            }
            result[count]=x;
            count++;
        }

        return  result;
    }


    public static int[] getMultiple1(int[] array){
        int n = array.length;
        int[] left = new int[n];
        int[] right = new int[n];
        int[] result = new int[n];

        left[0]=1;
        right[n-1]=1;

        for(int i=1;i<n;i++){
         left[i]= array[i-1]*left[i-1];
        }

        for(int j = n-2;j>=0;j--){
            right[j]= array[j+1]*right[j+1];
        }

        for(int i=0;i<n;i++){
            result[i]= left[i]*right[i];
        }

        return result;
    }
}
