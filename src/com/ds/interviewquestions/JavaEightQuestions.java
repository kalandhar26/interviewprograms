package com.ds.interviewquestions;

import com.ds.java21.streams.GenerateList;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class JavaEightQuestions {

    public static void main(String[] args) {

        GenerateList list = new GenerateList();

        List<Employee> employeeList = list.generateEmployeesList();

        System.out.println("==========Print Maximum Salary from Collection=================");
        Employee employee = employeeList.stream().max(Comparator.comparingDouble(Employee::getSalary)).get();
        System.out.println(employee);

        System.out.println("==========Print Minimum Salary from Collection=================");
        Employee employee1 = employeeList.stream().min(Comparator.comparingDouble(Employee::getSalary)).get();
        System.out.println(employee1);

        System.out.println("==========Print Max or Min Salary From Each Department=================");
        Map<Integer, Optional<Employee>> collect = employeeList.stream().collect(Collectors.groupingBy(Employee::getDeptid, Collectors.maxBy(Comparator.comparingDouble(Employee::getSalary))));
        System.out.println(collect);

        System.out.println("==========Print Only Inactive Employees from list=================");
        List<Employee> inactiveEmployees = employeeList.stream().filter(emp -> emp.getStatus().equals("InActive")).toList();

        for (Employee e : inactiveEmployees) {
            System.out.println(e.toString());
        }

        System.out.println("==========Print Employee details based on deparment=================");
        Map<Integer, List<Employee>> collect1 = employeeList.stream().collect(Collectors.groupingBy(Employee::getDeptid, Collectors.toList()));
        System.out.println(collect1);

        System.out.println("==========Print Employee Count=================");
        Long collect3 = employeeList.stream().count();
        System.out.println(collect3);

        System.out.println("==========Print Employee Count department wise=================");
        Map<Integer, Long> collect2 = employeeList.stream().collect(Collectors.groupingBy(Employee::getDeptid, Collectors.counting()));
        System.out.println(collect2);

        System.out.println("==========Sort Employees based on their salaries desc order=================");
        List<Employee> collect4 = employeeList.stream().sorted(Comparator.comparingDouble(Employee::getSalary)).limit(3).collect(Collectors.toList());
        for (Employee e : collect4)
            System.out.println(e.toString());
        System.out.println("==========Sort Employees based on their salaries desc order ( Another way) =================");
        List<Employee> collect6 = employeeList.stream().sorted((o3, o4) -> (int) (o3.getSalary() - o4.getSalary())).collect(Collectors.toList());
        collect6.forEach(System.out::println);


        System.out.println("========== Sort Employees based on their salaries desc order=================");
        List<Employee> collect5 = employeeList.stream().sorted(Comparator.comparingDouble(Employee::getSalary).reversed()).limit(3).collect(Collectors.toList());
        for (Employee e : collect5)
            System.out.println(e.toString());

        System.out.println("==========Print All Employee except top 3 salaried employees=================");
        employeeList.stream().sorted((o5, o6) -> (int) (o6.getSalary() - o5.getSalary())).skip(3).forEach(System.out::println);

        System.out.println("=========================== Count No of Male and Female Employees=============");
        Map<String, Long> collect7 = employeeList.stream().collect(Collectors.groupingBy(Employee::getGender, Collectors.counting()));
        System.out.println(collect7);

        System.out.println("=========================== Print Employees departments wise=============");
        Map<String, List<Employee>> collect8 = employeeList.stream().distinct().collect(Collectors.groupingBy(Employee::getDepartment));
        System.out.println(collect8);

        System.out.println("===========================Print All available deparments=============");
        employeeList.stream().map(Employee::getDepartment).distinct().forEach(System.out::println);

        System.out.println("=========================== Average age of Male and Female employees=============");
        Map<String, Double> collect9 = employeeList.stream().collect(Collectors.groupingBy(Employee::getGender, Collectors.averagingInt(Employee::getAge)));
        System.out.println(collect9);

        System.out.println("=========================== Highest Paid Employee =============");
        Employee highestpaidEmployee = employeeList.stream().max(Comparator.comparingDouble(Employee::getSalary)).get();
        System.out.println(highestpaidEmployee.toString());


        System.out.println("=========================== 2nd Highest Paid Employee =============");
        Optional<Employee> secondHighestpaidEmployee = employeeList.stream().sorted(Comparator.comparingDouble(Employee::getSalary)).skip(1).findFirst();
        employeeList.stream().mapToDouble(Employee::getSalary).distinct().boxed().sorted(Double::compare).skip(1).findFirst();
        System.out.println("2nd ====$%^" + secondHighestpaidEmployee.toString());

        System.out.println("=========================== Highest Paid Employee Gender wise =============");

        Map<String, Optional<Employee>> collect10 = employeeList.stream().collect(Collectors.groupingBy(Employee::getGender, Collectors.maxBy(Comparator.comparingDouble(Employee::getSalary))));
        System.out.println(collect10);

        System.out.println("=========================== Highest Paid Employee Department wise =============");

        Map<String, Optional<Employee>> collect11 = employeeList.stream().collect(Collectors.groupingBy(Employee::getDepartment, Collectors.maxBy(Comparator.comparingDouble(Employee::getSalary))));
        System.out.println(collect11);

        System.out.println("===========================Employees who passedout after 2020 =============");

        employeeList.stream().filter(x -> x.getPassedOutyear() > 2020).toList().forEach(System.out::println);

        employeeList.stream().filter(x -> x.getPassedOutyear() > 2020).map(Employee::getName).forEach(System.out::println);

        System.out.println("===========================Count no of Employees Department wise =============");

        Map<String, Long> collect12 = employeeList.stream().collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting()));
        System.out.println(collect12);

        System.out.println("=========================== Average Salary of Employee Departmentwise =============");

        Map<String, Double> collect13 = employeeList.stream().collect(Collectors.groupingBy(Employee::getDepartment, Collectors.averagingDouble(Employee::getSalary)));

        System.out.println(collect13);

        System.out.println("========================== Youngest Employee ==========================");

        employeeList.stream().min(Comparator.comparingInt(Employee::getAge)).map(Employee::getName).ifPresent(System.out::println);

        System.out.println("========================== Youngest MALE Employee ==========================");

        Map<String, Optional<Employee>> collect14 = employeeList.stream().collect(Collectors.groupingBy(Employee::getGender, Collectors.minBy(Comparator.comparingInt(Employee::getAge))));
        System.out.println(collect14);

        String youngestMaleEmployee = employeeList.stream().filter(e -> e.getGender().equals("MALE")).min(Comparator.comparingInt(Employee::getAge)).map(Employee::getName).get();
        System.out.println(youngestMaleEmployee);

        System.out.println("========================== Total Salary needed to Organization to pay for Employees ==========================");

        Double collect15 = employeeList.stream().mapToDouble(Employee::getSalary).sum();
        System.out.println(collect15);

        System.out.println("========================== Average Salary of Each Employee ==========================");
        Double collect16 = employeeList.stream().collect(Collectors.averagingDouble(Employee::getSalary));
        System.out.println(collect16);

        System.out.println("========================== List Out Minor Employees ==========================");
        employeeList.stream().filter(e -> e.getAge() < 18).map(Employee::getName).toList().forEach(System.out::println);

        System.out.println("========================== Increase Salary for employees who age is > 20 (Only hiked Salaries employees are displayed) ==========================");
        employeeList.stream().filter(age -> age.getAge() > 20).peek(employee2 -> {
            double hikedSalary = employee2.getSalary() * 1.1;
            employee2.setSalary(hikedSalary);
        }).toList().forEach(System.out::println);

        System.out.println("========================== All Employees are displayed but hiked for only who has age above 25  ==========================");
        employeeList.stream().peek(employee3 -> {
            if (employee3.getAge() > 25) {
                employee3.setSalary(employee3.getSalary() * 1.1);
            }
        }).toList().forEach(System.out::println);

        System.out.println("========================== Filter Employee who name starts with R and contains reddy ==========================");
        employeeList.stream().filter(s -> s.getName().toLowerCase().startsWith("r")).toList().forEach(System.out::println);
        employeeList.stream().filter(s -> s.getName().toLowerCase().contains("reddy")).toList().forEach(System.out::println);

        System.out.println("========================== find Unique passout years in desc Order==========================");
        employeeList.stream().sorted(Comparator.comparingDouble(Employee::getPassedOutyear).reversed()).map(Employee::getPassedOutyear).distinct().toList().forEach(System.out::println);

        System.out.println("========================== find dulicate passout years ==========================");

        employeeList.stream()
                .map(Employee::getPassedOutyear).filter(passedOutyear -> employeeList
                        .stream()
                        .filter(passoutyear -> passoutyear.getPassedOutyear() == passedOutyear).count() > 1
                ).distinct().toList().forEach(System.out::println);

        System.out.println("==================== Generate List of Minor and Major ===================");
        Map<Boolean, List<Employee>> collect17 = employeeList.stream().collect(Collectors.partitioningBy(emp -> emp.getAge() > 18));

        for (Map.Entry<Boolean, List<Employee>> entry : collect17.entrySet()) {
            if (entry.getKey()) {
                System.out.println(" =======Employees who are majors=========");
                System.out.println(entry.getValue());
            } else {
                System.out.println("========== Minor Employees===============");
                System.out.println(entry.getValue());
            }

            List<Employee> list1 = entry.getValue();
            for (Employee e : list1) {
                System.out.println(e.getName());
            }
        }

        // List Out all the hobbies
        System.out.println("============== Employees Hobbies =======================");
        List<String> collect18 = employeeList.stream().map(Employee::getHobbies).flatMap(List::stream).distinct().toList();

        for (String list1 : collect18) {
            System.out.println(list1);
        }

        // Count Age
        double v = employeeList.stream().mapToDouble(Employee::getAge).sum();
        System.out.println(v);
        // =====================  End  =============================


    }
}
