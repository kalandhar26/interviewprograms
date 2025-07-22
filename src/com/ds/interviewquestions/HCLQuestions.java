package com.ds.interviewquestions;

import com.ds.java21.streams.GenerateList;

import java.util.List;

public class HCLQuestions {

    public static void main(String[] args) {

        GenerateList list = new GenerateList();
        List<Employee> employeeList = list.generateEmployeesList();

        System.out.println("===========Employee Names > 25 age =======================");
        // Employees who age is greater than 25
        employeeList.stream().filter(employee -> employee.getAge()>25).map(Employee::getName).forEach(System.out::println);
    }
}
