package com.ds.interviewquestions;

import com.ds.java21.streams.GenerateList;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ThirdHighestSalariedEmployee {

    public static void main(String[] args) {

        GenerateList list = new GenerateList();
        List<Employee> employeeList = list.generateEmployeesList();

        String first = employeeList.stream().distinct().sorted(Comparator.comparingDouble(Employee::getSalary).reversed()).map(Employee::getName).skip(2).findFirst().orElse(null);
       String employee =  employeeList.stream().distinct().sorted(Comparator.comparingDouble(Employee::getSalary).reversed()).map(emp -> emp.getName()+" - "+emp.getSalary()).skip(2).findFirst().orElse(null);

        System.out.println(first);
        System.out.println(employee);
    }
}
