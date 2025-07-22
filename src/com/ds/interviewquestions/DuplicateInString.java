package com.ds.interviewquestions;

import com.ds.java21.streams.GenerateList;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DuplicateInString{


    public static void main(String[] args) {

        GenerateList list = new GenerateList();
        List<Employee> employeeList = list.generateEmployeesList();
        String input = "I love my wife , because my wife is love of my life.";


        // 1. Find Words Starting with 'A' from a list and collect them
        System.out.println("================== Names starts with R  ===========");
        employeeList.stream().filter(name -> name.getName().startsWith("R")).map(Employee::getName).toList().forEach(System.out::println);

        // 2. Filter Employees based on their gender
        Map<String, List<Employee>> collect = employeeList.stream().collect(Collectors.groupingBy(Employee::getGender));

        for (Map.Entry<String, List<Employee>> entry : collect.entrySet()) {

            if (entry.getKey().equalsIgnoreCase("Male")) {
                System.out.println("================== MALE  Employees  ===========");
            } else {
                System.out.println("================== FEMALE Employees ===========");
            }

            List<Employee> empList = entry.getValue();

            for (Employee e : empList) {
                System.out.println(e.getName());
            }
        }

        // 3. Find duplicate words in a String and Count them

        String[] s = input.split(" ");
        List<String> list1 = Arrays.asList(s);
        List<String> duplicateList = list1.stream().filter(dup -> list1.stream().filter(dup::equalsIgnoreCase).count() > 1).distinct().toList();
        System.out.println("================== Duplicate Values ===========");
        for (String e : duplicateList) {
            System.out.println(e);
        }

        // To count duplicate Values
        Map<String, Integer> duplicateValue = new HashMap<>();
        for (String word : list1) {
            // Check word is present in Map or not (Initially it returns null
            Integer wordCount = duplicateValue.get(word);
            if (wordCount == null)
                wordCount = 0;

            duplicateValue.put(word, wordCount + 1);
        }

        for (Map.Entry<String, Integer> entry : duplicateValue.entrySet()) {
            if (entry.getValue() > 1)
                System.out.println(entry.getKey() + " -> " + entry.getValue());
        }

        // Different way to iterate Map
        duplicateValue.forEach((key, value) -> {
            if (value > 1)
                System.out.println(key + "==> " + value);
        });
        // 4. Create Custom Function interface and use it in main method.


        // create instance of custom functional interface
        CustomFunctionalInterface addition = Integer::sum;

        // use functional interface instance to call the method
        int sumofIntegers = addition.sum(5, 9);

        System.out.println("================== Custom F I ===========");
        System.out.println(sumofIntegers);
    }
}
