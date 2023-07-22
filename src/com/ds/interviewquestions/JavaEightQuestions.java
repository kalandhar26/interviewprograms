package com.ds.interviewquestions;

import java.util.*;
import java.util.stream.Collectors;

public class JavaEightQuestions {


    public  static List<Employee> getEmployeeList(){

        List<String> list1 = new ArrayList<>();
        list1.add("Batting");
        list1.add("Bowling");
        list1.add("Fielding");
        List<String> list2 = new ArrayList<>();
        list2.add("Keeping");
        list2.add("umpiring");
        list2.add("stumping");
        List<String> list3 = new ArrayList<>();
        list3.add("spot fixing");
        list3.add("match fixing");
        list3.add("ball tampering");



        Employee e1 = new Employee(1, 2000, 1, "Active", "BabaKalandhar", 26, "MALE", "CSE",list1 ,2018);
        Employee e2 = new Employee(2, 30000, 1, "InActive", "SharinAlavudeen", 23, "FEMALE", "IT", list2,2021);
        Employee e3 = new Employee(3, 40000, 1, "Active", "RukiyaAnjum", 16, "FEMALE", "ECE", list3,2023);
        Employee e4 = new Employee(4, 50000, 2, "InActive", "NikilaReddy", 18, "FEMALE", "EEE",list1 , 2022);
        Employee e5 = new Employee(5, 60000, 2, "Active", "HarshithaReddy", 20, "FEMALE", "IT",list2, 2021);
        Employee e6 = new Employee(6, 70000, 2, "InActive", "RubyRani", 21, "FEMALE", "CSE",list1 , 2020);
        Employee e7 = new Employee(7, 80000, 3, "Active", "AnkithaDave", 35, "FEMALE", "EEE", list3,2010);
        Employee e8 = new Employee(8, 90000, 3, "InActive", "ViratKohli", 40, "MALE", "IT",list2, 2005);
        Employee e9 = new Employee(9, 10000, 3, "Active", "RajaKalandhar", 24, "MALE", "ECE",list1 , 2015);

        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(e1);
        employeeList.add(e2);
        employeeList.add(e3);
        employeeList.add(e4);
        employeeList.add(e5);
        employeeList.add(e6);
        employeeList.add(e7);
        employeeList.add(e8);
        employeeList.add(e9);

        return employeeList;

    }

    public static void main(String[] args) {

        List<Employee> employeeList = getEmployeeList();

        System.out.println("==========Print Maximum Salary from Collection=================");
        Employee employee = employeeList.stream().max(Comparator.comparingDouble(Employee::getSalary)).get();
        System.out.println(employee.toString());

        System.out.println("==========Print Minimum Salary from Collection=================");

        Employee employee1 = employeeList.stream().min(Comparator.comparingDouble(Employee::getSalary)).get();
        System.out.println(employee1.toString());

        System.out.println("==========Print Max or Min Salary From Each Department=================");
        Map<Integer, Optional<Employee>> collect = employeeList.stream().collect(Collectors.groupingBy(Employee::getDeptid, Collectors.maxBy(Comparator.comparingDouble(Employee::getSalary))));
        System.out.println(collect);

        System.out.println("==========Print Only Inactive Employees from list=================");
        List<Employee> inactiveEmployees = employeeList.stream().filter(emp -> emp.getStatus().equals("InActive")).collect(Collectors.toList());

        for (Employee e : inactiveEmployees) {
            System.out.println(e.toString());
        }

        System.out.println("==========Print Employee details based on deparment=================");
        Map<Integer, List<Employee>> collect1 = employeeList.stream().collect(Collectors.groupingBy(Employee::getDeptid, Collectors.toList()));
        System.out.println(collect1);

        System.out.println("==========Print Employee Count=================");
        Long collect3 = employeeList.stream().collect(Collectors.counting());
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
        employeeList.stream().sorted((o5, o6) -> (int) (o6.getSalary() - o5.getSalary())).skip(3).collect(Collectors.toList()).forEach(System.out::println);

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

        System.out.println("=========================== Highest Paid Employee Gender wise =============");

        Map<String, Optional<Employee>> collect10 = employeeList.stream().collect(Collectors.groupingBy(Employee::getGender, Collectors.maxBy(Comparator.comparingDouble(Employee::getSalary))));
        System.out.println(collect10);

        System.out.println("=========================== Highest Paid Employee Department wise =============");

        Map<String, Optional<Employee>> collect11 = employeeList.stream().collect(Collectors.groupingBy(Employee::getDepartment, Collectors.maxBy(Comparator.comparingDouble(Employee::getSalary))));
        System.out.println(collect11);

        System.out.println("===========================Employees who passedout after 2020 =============");

        employeeList.stream().filter(x -> x.getPassedOutyear() > 2020).collect(Collectors.toList()).forEach(System.out::println);

        employeeList.stream().filter(x -> x.getPassedOutyear() > 2020).map(Employee::getName).forEach(System.out::println);

        System.out.println("===========================Count no of Employees Department wise =============");

        Map<String, Long> collect12 = employeeList.stream().collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting()));
        System.out.println(collect12);

        System.out.println("=========================== Average Salary of Employee Departmentwise =============");

        Map<String, Double> collect13 = employeeList.stream().collect(Collectors.groupingBy(Employee::getDepartment, Collectors.averagingDouble(Employee::getSalary)));

        System.out.println(collect13);

        System.out.println("========================== Youngest Employee ==========================");

        String youngestEmployee = employeeList.stream().min(Comparator.comparingInt(Employee::getAge)).map(Employee::getName).get();
        System.out.println(youngestEmployee);

        System.out.println("========================== Youngest MALE Employee ==========================");

        Map<String, Optional<Employee>> collect14 = employeeList.stream().collect(Collectors.groupingBy(Employee::getGender, Collectors.minBy(Comparator.comparingInt(Employee::getAge))));
        System.out.println(collect14);

        String youngestMaleEmployee = employeeList.stream().filter(e -> e.getGender().equals("MALE")).min(Comparator.comparingInt(Employee::getAge)).map(Employee::getName).get();
        System.out.println(youngestMaleEmployee);

        System.out.println("========================== Total Salary needed to Organization to pay for Employees ==========================");

        Double collect15 = employeeList.stream().collect(Collectors.summingDouble(Employee::getSalary));
        System.out.println(collect15);

        System.out.println("========================== Average Salary of Each Employee ==========================");
        Double collect16 = employeeList.stream().collect(Collectors.averagingDouble(Employee::getSalary));
        System.out.println(collect16);

        System.out.println("========================== List Out Minor Employees ==========================");
        employeeList.stream().filter(e -> e.getAge() < 18).map(Employee::getName).collect(Collectors.toList()).forEach(System.out::println);

        System.out.println("========================== Increase Salary for employees who age is > 20 (Only hiked Salaries employees are displayed) ==========================");
        employeeList.stream().filter(age -> age.getAge() > 20).map(employee2 -> {
            double hikedSalary = employee2.getSalary() * 1.1;
            employee2.setSalary(hikedSalary);
            return employee2;
        }).collect(Collectors.toList()).forEach(System.out::println);

        System.out.println("========================== All Employees are displayed but hiked for only who has age above 25  ==========================");
        employeeList.stream().map(employee3 -> {
            if (employee3.getAge() > 25) {
                employee3.setSalary(employee3.getSalary() * 1.1);
            }
            return employee3;
        }).collect(Collectors.toList()).forEach(System.out::println);

        System.out.println("========================== Filter Employee who name starts with R and contains reddy ==========================");
        employeeList.stream().filter(s -> s.getName().toLowerCase().startsWith("r")).collect(Collectors.toList()).forEach(System.out::println);
        employeeList.stream().filter(s -> s.getName().toLowerCase().contains("reddy")).collect(Collectors.toList()).forEach(System.out::println);

        System.out.println("========================== find Unique passout years in desc Order==========================");
        employeeList.stream().sorted(Comparator.comparingDouble(Employee::getPassedOutyear).reversed()).map(Employee::getPassedOutyear).distinct().collect(Collectors.toList()).forEach(System.out::println);

        System.out.println("========================== find dulicate passout years ==========================");

       employeeList.stream()
                .filter(duplicates -> employeeList
                        .stream()
                        .filter(passoutyear -> passoutyear.getPassedOutyear() == duplicates.getPassedOutyear()).count() > 1
                ).map(Employee::getPassedOutyear).distinct().collect(Collectors.toList()).forEach(System.out::println);

        System.out.println("==================== Generate List of Minor and Major ===================");
        Map<Boolean, List<Employee>> collect17 = employeeList.stream().collect(Collectors.partitioningBy(emp -> emp.getAge() > 18));

        for(Map.Entry<Boolean, List<Employee>> entry : collect17.entrySet()){
            if(entry.getKey()){
                System.out.println(" =======Employees who are majors=========");
            }else {
                System.out.println("========== Minor Employees===============");
            }

            List<Employee> list = entry.getValue();
            for(Employee e : list){
                System.out.println(e.getName());
            }
        }

        // List Out all the hobbies
        System.out.println("============== Employees Hobbies =======================");
        List<String> collect18 = employeeList.stream().map(Employee::getHobbies).flatMap(List::stream).distinct().collect(Collectors.toList());

        for(String list : collect18){
            System.out.println(list);
        }

        // =====================  End  =============================
    }
}
