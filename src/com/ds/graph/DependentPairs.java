package com.ds.graph;

import java.util.*;

public class DependentPairs {

    public static void main(String[] args) {

        List<String[]> pairs = Arrays.asList(
               new String[] {"A","B"},
                new String[] {"B","A"},
                new String[] {"A","D"},
                new String[] {"B","F"},
                new String[] {"F","L"},
                new String[] {"G","B"}
                );
        System.out.println(dependencyPairs(pairs, "A"));

    }

    public static List<String> dependencyPairs(List<String[]> pairs, String target){
        Map<String, List<String>> graph = new HashMap<>();

        for(String[] pair : pairs){
            String first = pair[0];
            String second = pair[1];
            graph.computeIfAbsent(first, k -> new ArrayList<>()).add(second);
        }

        Set<String> visited = new HashSet<>();
        List<String> neighbours = new ArrayList<>();

        Queue<String> queue = new LinkedList<>(graph.getOrDefault(target, Collections.emptyList()));


        while(!queue.isEmpty()){
            String node = queue.poll();

            if(!visited.contains(node)){
                visited.add(node);
                neighbours.add(node);
                queue.addAll(graph.getOrDefault(node,Collections.emptyList()));
            }
        }

        return  neighbours;
    }
}
