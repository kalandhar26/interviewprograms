package com.java;

import java.util.HashMap;
import java.util.Map;

public class IsoMorphic {

    public static void main(String[] args) {

        String s1 = "aababbb";
        String s2 = "rryryyy";

        if(isIsomorphic(s1,s2)){
            System.out.println("IsoMorphic");
        }else{
            System.out.println("Non IsoMorphic");
        }
    }

    public static boolean  isIsomorphic(String s1, String s2){
        if(s1.length() != s2.length())
            return false;

        Map<Character, Character> map1 = new HashMap<>();
        Map<Character, Boolean> map2 = new HashMap<>();


        for(int i=0;i<s1.length();i++){
            char c1 = s1.charAt(i);
            char c2 = s2.charAt(i);

            if(map1.containsKey(c1)){
                if(map1.get(c1) != c2){
                    return false;
                }
            }else{
                if(map2.containsKey(c2)){
                    return false;
                }

                map1.put(c1,c2);
                map2.put(c2,true);
            }
        }

        return true;
    }
}
