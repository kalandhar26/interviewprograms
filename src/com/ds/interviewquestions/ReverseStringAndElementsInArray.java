package com.ds.interviewquestions;

public class ReverseStringAndElementsInArray {


    public static void main(String[] args) {

        String[] array = {"baba","kala","vankay","bendakaya"};

        String[] strings = reverseStringAndElementsInArray(array);

        for(String x : strings)
            System.out.println(x);

    }

    public static String[] reverseStringAndElementsInArray(String[] array){
        int n = array.length;

        String[] result = new String[n];
        int count=0;

        for(int a=n-1;a>=0;a--){
            String currentString = array[a];
            int m = currentString.length();
            char[] reverseChars = new char[m];
            for(int i=m-1,j=0;i>=0;i--,j++){
                reverseChars[j]= currentString.charAt(i);
            }
            result[count] = new String(reverseChars);
            count++;
        }
        return result;
    }


}
