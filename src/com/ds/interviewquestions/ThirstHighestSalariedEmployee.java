package com.ds.interviewquestions;

import com.ds.java21.streams.GenerateList;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ThirstHighestSalariedEmployee {

    public static void main(String[] args) {

        GenerateList list = new GenerateList();
        List<Employee> employeeList = list.generateEmployeesList();

        String first = employeeList.stream().sorted(Comparator.comparingDouble(Employee::getSalary).reversed()).map(Employee::getName).skip(2).findFirst().get();

        System.out.println(first);
    }
}
