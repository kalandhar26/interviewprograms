package com.ds.interviewquestions;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ThirstHighestSalariedEmployee {

    public static void main(String[] args) {

        List<Employee> empList = JavaEightQuestions.getEmployeeList();

        String first = empList.stream().sorted(Comparator.comparingDouble(Employee::getSalary).reversed()).map(Employee::getName).skip(2).findFirst().get();

        System.out.println(first);
    }
}
