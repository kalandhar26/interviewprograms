package com.ds.iterates;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TraverseHashMap {

    public static void main(String[] args) {

        // There are 5 ways

        Map<Integer, String> map = new HashMap<>();
        map.put(1,"Anjum");
        map.put(2,"Baba");
        map.put(3,"Kala");
        map.put(4,"Rukiya");
        map.put(5,"My Love");

        way1(map);
        way2(map);
        way3(map);
        way4(map);
        way5(map);
    }

    // use HashMap EntrySet and Iterator
    public static void way1(Map<Integer, String> map){
        Iterator<Map.Entry<Integer, String>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<Integer, String> entry = iterator.next();
            System.out.println(entry.getKey()+" "+entry.getValue());
        }
    }

    public static void way2(Map<Integer, String> map){

    }

    public static void way3(Map<Integer, String> map){

    }

    public static void way4(Map<Integer, String> map){

    }

    public static void way5(Map<Integer, String> map){

    }
}
