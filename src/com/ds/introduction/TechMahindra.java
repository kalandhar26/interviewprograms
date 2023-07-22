package com.ds.introduction;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TechMahindra {

    public static void main(String[] args) {

        List<Integer> integerList = new ArrayList<>();

        integerList.add(1);
        integerList.add(2);
        integerList.add(2);
        integerList.add(3);
        integerList.add(1);
        integerList.add(4);

        List<Integer> list = integerList.stream().filter(duplicate -> integerList.stream().filter(duplicate1 -> duplicate.equals(duplicate)).count()>1).distinct().collect(Collectors.toList());

        for(Integer i : list){
            System.out.println(i);
        }

    }
}
