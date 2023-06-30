package com.ds.interviewquestions;

import java.util.List;

public class HCLQuestions {

    public static void main(String[] args) {

        List<Employee> employeeList = JavaEightQuestions.getEmployeeList();

        System.out.println("===========Employee Names > 25 age =======================");
        // Employees who age is greater than 25
        employeeList.stream().filter(employee -> employee.getAge()>25).map(Employee::getName).forEach(System.out::println);
    }
}
