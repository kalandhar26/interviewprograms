package com.ds.interviewquestions;

public class ReverseEachStringInArray {

    public static void main(String[] args) {
        String[] array = {"baba","kala","vankay","bendakaya"};

        String[] strings = reverseStringInArray1(array);

        for(String x : strings)
            System.out.println(x);
    }

    public static String[] reverseStringInArray(String[] array){
        int n = array.length;

        String[] result = new String[n];
        int count=0;

        for(String x : array){
            StringBuilder sb = new StringBuilder(x);
            result[count]=sb.reverse().toString();
            count++;
        }
        return result;
    }

    public static String[] reverseStringInArray1(String[] array){
        int n = array.length;

        String[] result = new String[n];
        int count=0;

        for(String x : array){
            int m = x.length();
            char[] reverseChars = new char[m];
            for(int i=0;i<m;i++){
                reverseChars[i]= x.charAt(m-1-i);
            }
            result[count] = new String(reverseChars);
            count++;
        }
        return result;
    }
}
