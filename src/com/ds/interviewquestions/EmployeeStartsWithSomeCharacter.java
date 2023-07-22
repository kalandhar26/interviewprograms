package com.ds.interviewquestions;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EmployeeStartsWithSomeCharacter {

    public static void main(String[] args) {

        List<Employee> employeeList = JavaEightQuestions.getEmployeeList();

        Map<String, Double> checkStatusWiseAvgAge = employeeList.stream()
                .filter(p -> p.getName().startsWith("R"))
                .collect(Collectors.groupingBy(Employee::getStatus, Collectors.averagingDouble(Employee::getAge)));

        for(Map.Entry<String, Double> entry : checkStatusWiseAvgAge.entrySet()){
               if(entry.getKey().equalsIgnoreCase("active")){
                   System.out.println(" Average Age of Active Employeees is "+entry.getValue());
               }else {
                   System.out.println(" Average Age of Inactive Employees is "+entry.getValue());
               }
        }


        Double averageAgeOfMale = employeeList.stream().filter(e -> e.getStatus().equalsIgnoreCase("active")).collect(Collectors.averagingDouble(Employee::getAge));
        System.out.println(averageAgeOfMale);


    }
}
