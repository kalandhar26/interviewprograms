package com.ds.interviewquestions;

import java.util.HashMap;
import java.util.Map;

public class MergeMaps {

    public static void main(String[] args) {

        Map<String, String> map1 = new HashMap<>();
        map1.put("k1", "v1");
        map1.put("K2", "v2");

        Map<String, String> map2 = new HashMap<>();
        map2.put("k1", "v3");
        map2.put("K3", "v3");

        Map<String, String> mergedMap = mergeMaps(map1, map2);

        for (Map.Entry<String, String> entry : mergedMap.entrySet()) {
            System.out.println(entry.getKey() + "," + entry.getValue());
        }
    }

    public static Map<String, String> mergeMaps(Map<String, String> map1, Map<String, String> map2) {
        Map<String, String> mergedMap = new HashMap<>();

        for (Map.Entry<String, String> entry : map1.entrySet()) {
            String key = entry.getKey();
            String value = map2.containsKey(key) ? map2.get(key) : entry.getValue();
            mergedMap.put(key, value);
        }

        for (Map.Entry<String, String> entry : map2.entrySet()) {
            if (!map1.containsKey(entry.getKey())) {
                mergedMap.put(entry.getKey(), entry.getValue());
            }
        }

        return mergedMap;
    }
}
