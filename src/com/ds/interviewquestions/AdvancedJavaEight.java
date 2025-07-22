package com.ds.interviewquestions;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AdvancedJavaEight {

    public  static List<Employee> getEmployeeList(){

        // Hobbies List
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

        // Address 4 types
        Address a1 = new Address(1,"AandlaVanka","Pileru","AP","INDIA");
        Address a2 = new Address(2,"BandlaVanka","Aileru","BP","PAKISTAN");
        Address a3 = new Address(3,"CandlaVanka","Bileru","CP","BANGLADESH");
        Address a4 = new Address(4,"DandlaVanka","Kileru","DP","SRILANKA");

        // List of Departments
        Department d1 = new Department(1,"BTECH");
        Department d2 = new Department(2,"CIVIL");
        Department d3 = new Department(3,"MECHA");
        Department d4 = new Department(4,"BIOTEC");

        List<Department> departmentList1 = new ArrayList<>();
        departmentList1.add(d1);
        List<Department> departmentList2 = new ArrayList<>();
        departmentList2.add(d2);
        List<Department> departmentList3 = new ArrayList<>();
        departmentList3.add(d3);
        List<Department> departmentList4 = new ArrayList<>();
        departmentList4.add(d4);

        Employee e1 = new Employee(1, 2000, 1, "Active", "BabaKalandhar", 26, "MALE", "CSE",list1,departmentList1,a1 ,2018);
        Employee e2 = new Employee(2, 30000, 1, "InActive", "SharinAlavudeen", 23, "FEMALE", "IT", list2,departmentList2,a2 ,2021);
        Employee e3 = new Employee(3, 40000, 1, "Active", "RukiyaAnjum", 16, "FEMALE", "ECE", list3,departmentList3,a3 ,2023);
        Employee e4 = new Employee(4, 50000, 2, "InActive", "NikilaReddy", 18, "FEMALE", "EEE",list1 ,departmentList4,a4 , 2022);
        Employee e5 = new Employee(5, 60000, 2, "Active", "HarshithaReddy", 20, "FEMALE", "IT",list2, departmentList1,a1 ,2021);
        Employee e6 = new Employee(6, 70000, 2, "InActive", "RubyRani", 21, "FEMALE", "CSE",list1 , departmentList2,a2 ,2020);
        Employee e7 = new Employee(7, 80000, 3, "Active", "AnkithaDave", 35, "FEMALE", "EEE", list3,departmentList3,a3 ,2010);
        Employee e8 = new Employee(8, 90000, 3, "InActive", "ViratKohli", 40, "MALE", "IT",list2,departmentList4,a4 , 2005);
        Employee e9 = new Employee(9, 10000, 3, "Active", "RajaKalandhar", 24, "MALE", "ECE",list1 , departmentList3,a1 ,2015);

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

//        2. I want Employees who belongs to Same City and State
//        2.  I want List of Employees who belongs to Same City and State and woks in Same Department
//        3.  I want List of Employees who are working in atleast 3 departments
//        4. I want list of employess who are working in utmost 2 departments
//        5. I want list of employees with atleast 3 common hobbies.

        System.out.println("===== Employees who are from same city and State ===========");

        // List<Employee> employeesFromGivenCityAndCountry =
                employeeList.stream().filter((employee) -> employee.getAddress().getCity().equalsIgnoreCase("pileru") && employee.getAddress().getState().equalsIgnoreCase("AP")).toList().forEach(System.out::println);

        System.out.println("===== Employees who are from same city ===========");

                employeeList.stream().collect(Collectors.groupingBy(employee -> employee.getAddress().getCity(),Collectors.toList())).forEach((k,v)->{
                    System.out.println(k+"=>"+v);
                });

        System.out.println("===== Employees who are from same department ===========");

        // Print whole employee Object
        Map<String, List<Employee>> departmentWiseEmployees = employeeList.stream().flatMap(employee -> employee.getDepartments().stream().map(department -> new AbstractMap.SimpleEntry<>(department.getName(), employee)))
                .collect(Collectors.groupingBy(AbstractMap.SimpleEntry::getKey, Collectors.mapping(AbstractMap.SimpleEntry::getValue, Collectors.toList())));

        departmentWiseEmployees.forEach((k,v)->{
            System.out.println(k+"=>"+v);
        });

        // Print only employee Name
        Map<String, List<String>> departmentWiseEmployeeName = employeeList.stream().flatMap(employee -> employee.getDepartments().stream().map(department -> new AbstractMap.SimpleEntry<>(department.getName(), employee.getName())))
                .collect(Collectors.groupingBy(AbstractMap.SimpleEntry::getKey, Collectors.mapping(AbstractMap.SimpleEntry::getValue, Collectors.toList())));

        departmentWiseEmployeeName.forEach((k,v)->{
            System.out.println(k+"=>"+v);
        });

        System.out.println("===List of Employees who are working in atleast 2 departments==========");
        List<String> employeesWorkingAtleast2Departments = employeeList.stream().filter(employee -> employee.getDepartments().size() >= 2).map(Employee::getName).toList();
        employeesWorkingAtleast2Departments.forEach(System.out::println);

        // EmployeeName -> List of Department Object
        Map<String, List<Department>> collect = employeeList.stream().filter(employee -> employee.getDepartments().size() >= 1).collect(Collectors.toMap(Employee::getName, Employee::getDepartments));
        collect.forEach((k,v)->{
            System.out.println(k+" "+v);
        });

        // EmployeeName -> List of DepartmentNames
        Map<String, List<String>> collect1 = employeeList.stream().filter(employee -> employee.getDepartments().size() >= 1).collect(Collectors.toMap(Employee::getName, employee -> employee.getDepartments().stream().map(Department::getName).toList()));
        collect1.forEach((k,v)->{
            System.out.println(k+" "+v);
        });

            System.out.println("==employees with atleast 3 common hobbies===");
        Map<String, List<Employee>> collect2 = employeeList.stream().filter(
                        employee -> {
                            long count = employee.getHobbies().stream().filter(hobbies -> employee.getHobbies().contains(hobbies)).count();
                            return count >= 3;
                        })
                .flatMap(employee -> employee.getHobbies().stream().map(hobbies -> new AbstractMap.SimpleEntry<>(hobbies, employee)))
                .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.mapping(Map.Entry::getValue, Collectors.toList())));

        collect2.forEach((k,v)->{
            System.out.println(k+"=>"+v);
        });





    }


}
