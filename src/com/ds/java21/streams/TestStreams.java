package com.ds.java21.streams;

import com.ds.interviewquestions.Employee;

import java.util.*;
import java.util.stream.Collectors;

public class TestStreams {

    public static void main(String[] args) {
        GenerateList list = new GenerateList();
        List<Employee> employees = list.generateEmployeesList();

        // Employees from Same State and City
        employees.stream().filter(employee -> "AP".equalsIgnoreCase(employee.getAddress().getState())
                        && "Pileru".equalsIgnoreCase(employee.getAddress().getCity()))
                .forEach(System.out::println);

        // Group Employee based on City
        employees.stream().collect(Collectors.groupingBy(emp -> emp.getAddress().getCity()))
                .forEach((k, v) -> System.out.println(k + " ->" + v));

        // Group Employee based on State
        employees.stream().collect(Collectors.groupingBy(emp -> emp.getAddress().getState()))
                .forEach((k, v) -> System.out.println(k + " ->" + v));

        System.out.println("============Youngest Employee ===================");
        employees.stream().min(Comparator.comparingDouble(Employee::getAge)).ifPresent(System.out::println);

        System.out.println("============Oldest Employee ===================");
        employees.stream().max(Comparator.comparingDouble(Employee::getAge)).ifPresent(System.out::println);

        System.out.println("============Lowest Salaried Employee ===================");
        employees.stream().min(Comparator.comparingDouble(Employee::getSalary)).ifPresent(System.out::println);

        System.out.println("============Highest Salaried Employee ===================");
        employees.stream().max(Comparator.comparingDouble(Employee::getSalary)).ifPresent(System.out::println);

        // ============= Print Max , Min Age or Highest, Lowest Category Wise
        // ==================

        System.out.println("============Highest Salaried Employee from Each Department ===================");
        employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment,
                        Collectors.maxBy(Comparator.comparingDouble(Employee::getSalary))))
                .forEach((k, v) -> System.out.println(k + "->" + v));
        employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.collectingAndThen(
                        Collectors.maxBy(Comparator.comparingDouble(Employee::getSalary)),
                        Optional::get)))
                .forEach((k, v) -> System.out.println(k + "->" + v));

        System.out.println("============Lowest Salaried Employee from Each Department ===================");
        employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment,
                        Collectors.minBy(Comparator.comparingDouble(Employee::getSalary))))
                .forEach((k, v) -> System.out.println(k + "->" + v));
        employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.collectingAndThen(
                        Collectors.minBy(Comparator.comparingDouble(Employee::getSalary)),
                        Optional::get)))
                .forEach((k, v) -> System.out.println(k + "->" + v));

        System.out.println("============ Print Employees based on Department ===================");
        employees.stream().collect(Collectors.groupingBy(Employee::getDepartment))
                .forEach((k, v) -> System.out.println(k + "->" + v));

        System.out.println("============ Print Employees based on Gender ===================");
        employees.stream().collect(Collectors.groupingBy(Employee::getGender, Collectors.toList()))
                .forEach((k, v) -> System.out.println(k + "->" + v));

        System.out.println("=========== Department Wise Employees ===============");

        employees.stream().collect(Collectors.groupingBy(Employee::getDepartment, Collectors.toList()))
                .forEach((k, v) -> System.out.println(k + "->" + v));

        System.out.println("=========== Majors and Minors ===============");
        employees.stream().collect(Collectors.partitioningBy(emp -> emp.getAge() > 18))
                .forEach((k, v) -> System.out.println(k + "->" + v));

        System.out.println("=========== Print all departments ===============");
        employees.stream().map(Employee::getDepartment).distinct().forEach(System.out::println);

        System.out.println("=========== Print all Hobbies ===============");
        employees.stream().flatMap(emp -> emp.getHobbies().stream()).distinct().map(String::toUpperCase)
                .forEach(System.out::println);
        employees.stream().map(Employee::getHobbies).flatMap(List::stream).distinct().map(String::toUpperCase)
                .forEach(System.out::println);

        System.out.println("=========== Print all States ===============");
        employees.stream().map(emp -> emp.getAddress().getState()).distinct().forEach(System.out::println);

        System.out.println("=========== Print all Cities ===============");
        employees.stream().map(emp -> emp.getAddress().getCity()).distinct().forEach(System.out::println);

        // Counting

        System.out.println("============ Count Employees DepartmentWise ===================");
        employees.stream().collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting()))
                .forEach((k, v) -> System.out.println(k + "->" + v));

        System.out.println("============ Count Employees based on Gender ===================");
        employees.stream().collect(Collectors.groupingBy(Employee::getGender, Collectors.counting()))
                .forEach((k, v) -> System.out.println(k + "->" + v));

        System.out.println("============ Count Employees based on State ===================");
        employees.stream()
                .collect(Collectors.groupingBy(emp -> emp.getAddress().getState(),
                        Collectors.counting()))
                .forEach((k, v) -> System.out.println(k + "->" + v));

        System.out.println("============ Count Employees based on City ===================");
        employees.stream()
                .collect(Collectors.groupingBy(emp -> emp.getAddress().getCity(),
                        Collectors.counting()))
                .forEach((k, v) -> System.out.println(k + "->" + v));

        System.out.println("============ Count Employees based on Hobbies ==================");
        employees.stream().collect(Collectors.groupingBy(emp -> emp.getHobbies(), Collectors.counting()))
                .forEach((k, v) -> System.out.println(k + "->" + v));

        // Average

        System.out.println("================= Average age of All Employees ===================");
        Double averageAge = employees.stream().collect(Collectors.averagingDouble(Employee::getAge));
        System.out.println(averageAge);

        System.out.println(
                "================= Average age of All Employees based on Gender ===================");
        employees.stream()
                .collect(Collectors.groupingBy(Employee::getGender,
                        Collectors.averagingDouble(Employee::getAge)))
                .forEach((k, v) -> System.out.println(k + "->" + v));

        System.out.println(
                "================= Average age of All Employees based on Department ==================");
        employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment,
                        Collectors.averagingDouble(Employee::getAge)))
                .forEach((k, v) -> System.out.println(k + "->" + v));

        System.out.println("================= Average age of All Employees based on State ==================");
        employees.stream()
                .collect(Collectors.groupingBy(emp -> emp.getAddress().getState(),
                        Collectors.averagingDouble(Employee::getAge)))
                .forEach((k, v) -> System.out.println(k + "->" + v));

        System.out.println("================= Average age of All Employees based on City =================");
        employees.stream()
                .collect(Collectors.groupingBy(emp -> emp.getAddress().getCity(),
                        Collectors.averagingDouble(Employee::getAge)))
                .forEach((k, v) -> System.out.println(k + "->" + v));

        System.out.println("================= Average Salary of All Employees ========================");
        Double averageSalary = employees.stream().collect(Collectors.averagingDouble(Employee::getSalary));
        System.out.println(averageSalary);

        System.out.println(
                "================= Average Salary of All Employees based on Gender ========================");
        employees.stream()
                .collect(Collectors.groupingBy(Employee::getGender,
                        Collectors.averagingDouble(Employee::getSalary)))
                .forEach((k, v) -> System.out.println(k + "->" + v));

        System.out.println(
                "================= Average Salary of All Employees based on Department ========================");
        employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment,
                        Collectors.averagingDouble(Employee::getSalary)))
                .forEach((k, v) -> System.out.println(k + "->" + v));

        System.out.println(
                "================= Average Salary of All Employees based on State ========================");
        employees.stream()
                .collect(Collectors.groupingBy(emp -> emp.getAddress().getState(),
                        Collectors.averagingDouble(Employee::getSalary)))
                .forEach((k, v) -> System.out.println(k + "->" + v));

        System.out.println(
                "================= Average Salary of All Employees based on City ======================");
        employees.stream()
                .collect(Collectors.groupingBy(emp -> emp.getAddress().getCity(),
                        Collectors.averagingDouble(Employee::getSalary)))
                .forEach((k, v) -> System.out.println(k + "->" + v));

        System.out.println("============ Total Salary needed to Organization to pay for Employees ===========");
        Double totalSalary = employees.stream().mapToDouble(Employee::getSalary).sum();
        System.out.println(totalSalary);

        System.out.println("==== Total Salary needed to Organization to pay for Employees based on Gender ===");
        employees.stream()
                .collect(Collectors.groupingBy(Employee::getGender,
                        Collectors.summingDouble(Employee::getSalary)))
                .forEach((k, v) -> System.out.println(k + "->" + v));

        System.out.println(
                "=== Total Salary needed to Organization to pay for Employees based on Department ===");
        employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment,
                        Collectors.summingDouble(Employee::getSalary)))
                .forEach((k, v) -> System.out.println(k + "->" + v));

        System.out.println("=== Total Salary need to Organization for Employees based on State ====");
        employees.stream()
                .collect(Collectors.groupingBy(emp -> emp.getAddress().getState(),
                        Collectors.summingDouble(Employee::getSalary)))
                .forEach((k, v) -> System.out.println(k + "->" + v));

        System.out.println("=== Total Salary need to Organization for Employees based on City ===");
        employees.stream()
                .collect(Collectors.groupingBy(emp -> emp.getAddress().getCity(),
                        Collectors.summingDouble(Employee::getSalary)))
                .forEach((k, v) -> System.out.println(k + "->" + v));

        // Sorting

        System.out.println("=== Sort Employee in asc order based on Salary ===");
        employees.stream().sorted(Comparator.comparingDouble(Employee::getSalary)).forEach(System.out::println);

        System.out.println("=== Sort Employee in desc order based on Salary ===");
        employees.stream().sorted(Comparator.comparingDouble(Employee::getSalary).reversed())
                .forEach(System.out::println);

        System.out.println("=== Sort Employee in asc order based on Age ===");
        employees.stream().sorted(Comparator.comparingDouble(Employee::getAge)).forEach(System.out::println);

        System.out.println("=== Sort Employee in desc order based on Age ===");
        employees.stream().sorted(Comparator.comparingDouble(Employee::getAge).reversed())
                .forEach(System.out::println);

        System.out.println("=== Sort Employee in asc order based on Name ===");
        employees.stream().sorted(Comparator.comparing(Employee::getName)).forEach(System.out::println);

        System.out.println("=== Sort Employee in desc order based on Name ===");
        employees.stream().sorted(Comparator.comparing(Employee::getName).reversed())
                .forEach(System.out::println);

        System.out.println("=== Sort Employee in asc order based on Gender ===");
        employees.stream().sorted(Comparator.comparing(Employee::getGender)).forEach(System.out::println);

        System.out.println("=== Sort Employee in desc order based on Gender ===");
        employees.stream().sorted(Comparator.comparing(Employee::getGender).reversed())
                .forEach(System.out::println);

        System.out.println("=== Sort Employee in asc order based on Department ===");
        employees.stream().sorted(Comparator.comparing(Employee::getDepartment)).forEach(System.out::println);

        System.out.println("=== Sort Employee in desc order based on Department ===");
        employees.stream().sorted(Comparator.comparing(Employee::getDepartment).reversed())
                .forEach(System.out::println);

        System.out.println("=== Sort Employee in asc order based on State ===");
        employees.stream().sorted(Comparator.comparing(emp -> emp.getAddress().getState()))
                .forEach(System.out::println);

        System.out.println("=== Sort Employee in desc order based on State ===");
        employees.stream()
                .sorted(Comparator.comparing((Employee emp) -> emp.getAddress().getState()).reversed())
                .forEach(System.out::println);

        System.out.println("=== Sort Employee in asc order based on City ===");
        employees.stream().sorted(Comparator.comparing(emp -> emp.getAddress().getCity()))
                .forEach(System.out::println);

        System.out.println("=== Sort Employee in desc order based on City ===");
        employees.stream().sorted(Comparator.comparing((Employee emp) -> emp.getAddress().getCity()).reversed())
                .forEach(System.out::println);

        System.out.println("=== Sort Employee in asc order based on Hobbies ===");
        employees.stream()
                .sorted(Comparator.comparing(emp -> String.valueOf(emp.getHobbies())))
                .forEach(System.out::println);

        System.out.println("=== Sort Employee in desc order based on Hobbies ===");
        employees.stream()
                .sorted(Comparator.comparing((Employee emp) -> String.valueOf(emp.getHobbies()))
                        .reversed())
                .forEach(System.out::println);

        // Grouping By
        System.out.println("=== Group Employee based on Status ===");
        employees.stream().collect(Collectors.groupingBy(Employee::getStatus, Collectors.toList()))
                .forEach((k, v) -> System.out.println(k + "->" + v));

        employees.stream().collect(Collectors.groupingBy(Employee::getStatus,
                Collectors.mapping(Employee::getName, Collectors.toList()))).forEach((k, v) -> System.out.println(k + "-> " + v));

        // Filters

        System.out.println("=== Name Starts with B === ");
        employees.stream().filter(e -> e.getName().startsWith("B")).forEach(System.out::println);
        employees.stream().map(Employee::getName).filter(name -> name.startsWith("B"))
                .forEach(System.out::println);

        System.out.println("=== Name Contains ler ===");
        employees.stream().filter(e -> e.getName().contains("ler")).forEach(System.out::println);

        System.out.println("=== Age greater than 18 ===");
        employees.stream().filter(e -> e.getAge() > 18).forEach(System.out::println);

        System.out.println("=== Employee who passed out after 2020 ===");
        employees.stream().filter(e -> e.getPassedOutyear() > 2020).map(Employee::getName)
                .forEach(System.out::println);

        System.out.println("=== Inactive Employee ===");
        employees.stream().filter(e -> e.getStatus().equalsIgnoreCase("inactive")).forEach(System.out::println);

        System.out.println("=== Increase Salary of Employee by 10% whose age is greater 30 ===");
        employees.stream().filter(e -> e.getAge() > 30)
                .map(e -> {
                    e.setSalary(e.getSalary() * 1.1);
                    return e;
                })
                .forEach(System.out::println);

        System.out.println("=== Employees Displayed, Salary Hiked for Age > 25 ===");
        employees.stream()
                .map(emp -> {
                    if (emp.getAge() > 25) {
                        emp.setSalary(emp.getSalary() * 1.1);
                    }
                    return emp;
                })
                .forEach(System.out::println);

        System.out.println("=== Total Age of Employees ===");
        double totalAge = employees.stream().mapToDouble(Employee::getAge).sum();
        System.out.println("Total Age: " + totalAge);


        System.out.println("=== Top Nth Highest Salary Employee ===");
        int n = 2;
        employees.stream().sorted(Comparator.comparingDouble(Employee::getSalary).reversed()).skip(n - 1).findFirst().ifPresent(System.out::println);

        System.out.println("==========Print All Employee except top \"N\" salaried employees=================");
        employees.stream().sorted(Comparator.comparingDouble(Employee::getSalary).reversed()).map(Employee::getName).skip(n).forEach(System.out::println);


        // Duplicates
        System.out.println("=== Print Duplicate All Cities ===");
        employees.stream().map(e -> e.getAddress().getCity()).distinct().forEach(System.out::println);

        System.out.println("=== Print Cities With AtLeast Two Employees ===");
        employees.stream()
                .filter(emp -> emp != null && emp.getAddress() != null && emp.getAddress().getCity() != null)
                .collect(Collectors.groupingBy(emp -> emp.getAddress().getCity(), Collectors.counting()))
                .entrySet().stream()
                .filter(entry -> entry.getValue() >= 2)
                .map(Map.Entry::getKey)
                .sorted()
                .forEach(System.out::println);

        System.out.println("=== employees working in at least N departments ===");
        int minDepartments = 1;
        employees.parallelStream()
                .filter(emp -> emp.getDepartments().size() >= minDepartments).forEach(System.out::println);

        System.out.println("=== employees with at least N common hobbies ===");

        int minCommonHobbies = 3;

        // Group employees by hobby and collect into a Map<String, List<Employee>>
        Map<String, List<Employee>> hobbyToEmployees = employees.stream()
                .filter(emp -> emp != null && emp.getHobbies() != null)
                .flatMap(emp -> emp.getHobbies().stream()
                        .filter(Objects::nonNull)
                        .map(hobby -> new AbstractMap.SimpleEntry<>(hobby.toLowerCase(), emp)))
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey,
                        Collectors.mapping(Map.Entry::getValue, Collectors.toList())
                ));

        // Filter hobbies with at least 3 employees and print
        hobbyToEmployees.entrySet().stream()
                .filter(entry -> entry.getValue().size() >= minCommonHobbies)
                .sorted(Comparator.comparing(Map.Entry::getKey))
                .forEach(entry -> System.out.println(entry.getKey() + " => " + entry.getValue()));
    }

}

