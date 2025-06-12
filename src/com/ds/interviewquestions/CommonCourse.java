package com.ds.interviewquestions;

import java.util.*;

public class CommonCourse {

    public static void main(String[] args) {
        String[][] input1 = {
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
        String[][] input = {
                {"0", "Advanced Mechanics"},
                {"0", "Art History"},
                {"1", "Course 1"},
                {"1", "Course 2"},
                {"2", "Computer Architecture"},
                {"3", "Course 1"},
                {"3", "Course 2"},
                {"4", "Algorithms"}
        };
        System.out.println(getCommonCourses(input1));
    }

    public static Map<String, Set<String>> getCommonCourses(String[][] enrollments){

        Map<String, Set<String>> output = new HashMap<>();

        Map<String,Set<String>> studentCourses = new HashMap<>();

        for(String[] enrollment : enrollments){
            String Id = enrollment[0];
            String course = enrollment[1];

            studentCourses.putIfAbsent(Id, new HashSet<>());
            studentCourses.get(Id).add(course);
        }
        System.out.println(studentCourses);
        List<String> studentIds = new ArrayList<>(studentCourses.keySet());
        int size = studentIds.size();

        for(int i=0;i<size;i++){
            String studentId1 = studentIds.get(i);
            for(int j=i+1;j<size;j++){
                String studentId2 = studentIds.get(j);
                Set<String> commonCourses = new HashSet<>(studentCourses.get(studentId1));
                commonCourses.retainAll(studentCourses.get(studentId2));
                String studentPair = studentId1+" "+studentId2;
                output.put(studentPair,commonCourses);
            }
        }

        return  output;
    }
}
