package com.java;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class SortMapByValue {

    public static void main(String[] args) {

        Map<String,Integer> map = new HashMap<>();
        map.put("Deepak",40000);
        map.put("BabaKalandhar",20000);
        map.put("Shahareen",30000);
        map.put("Anjum",50000);
        map.put("Sharin",80000);
        map.put("Vankay",70000);


        HashMap<String, Integer> collect = map.entrySet().stream().sorted(Map.Entry.comparingByValue()).collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (e1, e2) -> e1,
                LinkedHashMap::new
        ));

        collect.forEach((k,v)->System.out.println(k+"=>"+v));
    }
}
