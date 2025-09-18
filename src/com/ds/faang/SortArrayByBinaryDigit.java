package com.ds.faang;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class SortArrayByBinaryDigit {

    // sort based on number of 1
    public static void main(String[] args) {
        int[] array = new int[] {0,1,2,3,4,5,6,7,8,9,12,15};
        Map<Integer,Integer> map = new TreeMap<>();
        for(int x : array){
            String binaryString = Integer.toBinaryString(x);
            char[] binaryChar = binaryString.toCharArray();
            int count =0;
            for(char c : binaryChar){
                if(c=='1'){
                    count++;
                }
            }
            map.put(x,count);
        }
        List<Map.Entry<Integer, Integer>> list =
                map.entrySet().stream().sorted(Comparator.comparingInt(Map.Entry::getValue)).toList();
        System.out.println(list);
    }
}
