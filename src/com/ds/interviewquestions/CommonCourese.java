package com.ds.interviewquestions;

import  java.util.*;

public class CommonCourese {

    public static void main(String[] args) {

        String[][] input = {
                {"58", "Linear Algebra"},
                {"94", "Art History"},
                {"94", "Operating Systems"},
                {"17", "Software Design"},
                {"58", "Mechanics"},
                {"58", "Economics"},
                {"17", "Linear Algebra"},
                {"17", "Political Science"},
                {"94", "Economics"},
                {"25", "Economics"},
                {"58", "Software Design"}
        };

        String[][] enrollments2 = {
                {"0", "Advanced Mechanics"},
                {"0", "Art History"},
                {"1", "Course 1"},
                {"1", "Course 2"},
                {"2", "Computer Architecture"},
                {"3", "Course 1"},
                {"3", "Course 2"},
                {"4", "Algorithms"}
        };

        String[][] enrollments3 = {
                {"23", "Software Design"},
                {"3",  "Advanced Mechanics"},
                {"2",  "Art History"},
                {"33", "Another"},
        };

        Map<String,List<String>> map = getCommonCourses(enrollments3);

        map.forEach((k,v)-> {
            System.out.println('"'+k+'"'+":"+v);
        });

    }


    public static  Map<String,List<String>> getCommonCourses(String[][] input){

        Map<String, List<String>> result = new LinkedHashMap<>();

        Map<String, Set<String>> studentCourses = new HashMap<>();

        for(String[] array : input){
            String studentId = array[0];
            String courseName = array[1];
            studentCourses.putIfAbsent(studentId, new HashSet<>());
            studentCourses.get(studentId).add(courseName);

            List<String> studentIds = new ArrayList<>(studentCourses.keySet());

            int size = studentIds.size();

            for(int i=0;i<size;i++){
                for(int j = i+1;j<size;j++){
                    String student1 = studentIds.get(i);
                    String student2 = studentIds.get(j);

                    Set<String> sharedCourses = new LinkedHashSet<>(studentCourses.get(student1));
                    sharedCourses.retainAll(studentCourses.get(student2));

                    String pairKey = student2+","+student1;

                    result.put(pairKey,new ArrayList<>(sharedCourses));
                }
            }
        }

        return result;
    }
}
