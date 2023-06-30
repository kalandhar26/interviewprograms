package com.ds.interviewquestions;

import java.util.List;

public class Employee {

    private int id;

    private double salary;

    private int deptid;

    private String status;

    private String name;

    private int age;

    private String gender;

    private String department;

    private List<String> hobbies;


    private int passedOutyear;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getPassedOutyear() {
        return passedOutyear;
    }

    public void setPassedOutyear(int passedOutyear) {
        this.passedOutyear = passedOutyear;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public int getDeptid() {
        return deptid;
    }

    public void setDeptid(int deptid) {
        this.deptid = deptid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getHobbies() {
        return hobbies;
    }

    public Employee(int id, double salary, int deptid, String status, String name, int age, String gender, String department, List<String> hobbies, int passedOutyear) {
        this.id = id;
        this.salary = salary;
        this.deptid = deptid;
        this.status = status;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.department = department;
        this.hobbies = hobbies;
        this.passedOutyear = passedOutyear;
    }

    @Override
    public String toString() {
        return "Employee{" + "id=" + id + ", salary=" + salary + ", deptid=" + deptid + ", status='" + status + '\'' + ", name='" + name + '\'' + ", age=" + age + ", gender='" + gender + '\'' + ", department='" + department + '\'' + ", hobbies=" + hobbies + ", passedOutyear=" + passedOutyear + '}';
    }

    public void setHobbies(List<String> hobbies) {
        this.hobbies = hobbies;
    }
}
