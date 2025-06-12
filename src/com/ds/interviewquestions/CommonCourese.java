package com.ds.interviewquestions;

import  java.util.*;

public class CommonCourese {

    public static void main(String[] args) {

        String[][] enrollments1 = {
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

        Map<String,List<String>> map = getCommonCourses(enrollments1);
        Map<String,List<String>> map1 = getCommonCourses1(enrollments1);

        map.forEach((k,v)-> {
            System.out.println('"'+k+'"'+":"+v);
        });
        System.out.println("==================================");
        map1.forEach((k,v)-> {
            System.out.println('"'+k+'"'+":"+v);
        });

    }


    public static  Map<String,List<String>> getCommonCourses(String[][] input){

        // student1,student2 , Common Courses as List
        Map<String, List<String>> result = new LinkedHashMap<>();

        // StudentId, Common Course
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
                    studentCourses.get(student1);
                    Set<String> sharedCourses = new LinkedHashSet<>(studentCourses.get(student1));
                    sharedCourses.retainAll(studentCourses.get(student2));

                    String pairKey = student2+","+student1;

                    result.put(pairKey,new ArrayList<>(sharedCourses));
                }
            }
        }

        return result;
    }


    public static Map<String, List<String>> getCommonCourses1(String[][] input) {
        // StudentId, Common Course
        Map<String, Set<String>> studentCourses = new HashMap<>();

        // Build studentCourses map
        for (String[] array : input) {
            String studentId = array[0];
            String courseName = array[1];
            studentCourses.putIfAbsent(studentId, new HashSet<>());
            studentCourses.get(studentId).add(courseName);
        }

        // Find common courses for each student pair
        Map<String, List<String>> result = new LinkedHashMap<>();
        for (Map.Entry<String, Set<String>> entry1 : studentCourses.entrySet()) {
            for (Map.Entry<String, Set<String>> entry2 : studentCourses.entrySet()) {
                if (entry1.getKey().equals(entry2.getKey())) {
                    continue; // skip same student
                }
                String student1 = entry1.getKey();
                String student2 = entry2.getKey();
                Set<String> sharedCourses = new LinkedHashSet<>(entry1.getValue());
                sharedCourses.retainAll(entry2.getValue());
                String pairKey = student1 + "," + student2;
                result.put(pairKey, new ArrayList<>(sharedCourses));
            }
        }

        return result;
    }
}
