package com.java;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Test1 {

    public static void main(String[] args) {

        String input =  "Abdf Sghj Tryu Astq";

        String[] split = input.split(" ");

        List<String> collect = Arrays.stream(split).sorted((o1, o2) -> o1.compareTo(o2)).collect(Collectors.toList());

        int count =0;

//        for(int i=0;i<)
//        for(String x : split){
//            count = x.length();
//            for(int i=0;i<count;i++){
//
//            }
//        }

//            Map<String,String> map = new HashMap<>();
//          //  Map1=k1,v1 K2,v2 Map2=k1,v3 K3,v3 Output K1,v3 K3,v3
//
//                map.for((k,v)-> System.out.println(k+""+v));



           
    }
}
