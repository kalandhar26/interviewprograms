package com.ds.java21.streams;

import com.ds.interviewquestions.Address;
import com.ds.interviewquestions.Department;
import com.ds.interviewquestions.Employee;

import java.util.ArrayList;
import java.util.List;

public class GenerateList {

    public List<String> generateSportsHobbies() {
        List<String> list1 = new ArrayList<>();
        list1.add("Batting");
        list1.add("Bowling");
        list1.add("Fielding");
        list1.add("Cycling");
        list1.add("Swimming");
        list1.add("Trekking");
        list1.add("Football");
        list1.add("Basketball");
        list1.add("Table Tennis");
        list1.add("Gymnastics");

        return list1;

    }

    public List<String> generateSupportingHobbies() {

        List<String> list2 = new ArrayList<>();
        list2.add("Keeping");
        list2.add("Umpiring");
        list2.add("Stumping");
        list2.add("Scoring matches");
        list2.add("Photography");
        list2.add("Video editing");
        list2.add("Drone operating");
        list2.add("Sports commentary");
        list2.add("Analyst/Stat keeping");
        list2.add("Equipment maintenance");

        return list2;

    }

    public List<String> generateNegativeHobbies() {
        List<String> list3 = new ArrayList<>();
        list3.add("Spot fixing");
        list3.add("Match fixing");
        list3.add("Ball tampering");
        list3.add("Fake injuries");
        list3.add("Time wasting tactics");
        list3.add("Illegal betting");
        list3.add("Using banned substances");
        list3.add("On-field sledging");
        list3.add("Bribing umpires");
        list3.add("Tampering DRS reviews");

        return list3;
    }

    public List<Department> generateDepartmentList() {

        List<Department> departments = new ArrayList<>();
        departments.add(new Department(1, "BTECH"));
        departments.add(new Department(2, "CIVIL"));
        departments.add(new Department(3, "MECHA"));
        departments.add(new Department(4, "BIOTEC"));
        departments.add(new Department(5, "ECE"));
        departments.add(new Department(6, "EEE"));
        departments.add(new Department(7, "CSE"));
        departments.add(new Department(8, "IT"));
        departments.add(new Department(9, "CHEMICAL"));
        departments.add(new Department(10, "AERONAUTICS"));
        departments.add(new Department(11, "AGRICULTURE"));
        departments.add(new Department(12, "PHARMACY"));
        departments.add(new Department(13, "ROBOTICS"));
        departments.add(new Department(14, "NANOTECH"));
        departments.add(new Department(15, "MARINE"));
        departments.add(new Department(16, "BIOMEDICAL"));
        departments.add(new Department(17, "METALLURGY"));
        departments.add(new Department(18, "TEXTILE"));
        departments.add(new Department(19, "AUTOMOBILE"));
        departments.add(new Department(20, "ENVIRONMENTAL"));

        return departments;
    }

    public List<Address> generateAddressList() {
        List<Address> addresses = new ArrayList<>();
        addresses.add(new Address(1, "AandlaVanka", "Pileru", "AP", "INDIA"));
        addresses.add(new Address(2, "AnnamayyaCircle", "Rayachoti", "BP", "PAKISTAN"));
        addresses.add(new Address(3, "StateBank Street", "Angallu", "CP", "BANGLADESH"));
        addresses.add(new Address(4, "DandlaVanka", "Vayalpad", "DP", "SRILANKA"));
        addresses.add(new Address(5, "Uttana Colony", "Madanapalle", "EP", "INDIA"));
        addresses.add(new Address(6, "FandlaVanka", "Kurbalakota", "FP", "BHUTAN"));
        addresses.add(new Address(7, "Vailmiki Veedi", "Pileru", "GP", "CHINA"));
        addresses.add(new Address(8, "HandlaVanka", "Angallu", "AP", "USA"));
        addresses.add(new Address(9, "Vattakayala Palli", "Rayachoti", "IP", "UK"));
        addresses.add(new Address(10, "MirchiYard", "Kalikiri", "JP", "INDIA"));
        addresses.add(new Address(11, "KandlaVanka", "Sorakayapeta", "KP", "FRANCE"));
        addresses.add(new Address(12, "ChurchVeedi", "MirchiyardKota", "LP", "AUSTRALIA"));
        addresses.add(new Address(13, "MandlaVanka", "Kalakada", "MP", "RUSSIA"));
        addresses.add(new Address(14, "Randi Mandi", "Kalakada", "NP", "USA"));
        addresses.add(new Address(15, "GodumaPuvvu", "Angallu", "OP", "BRAZIL"));
        addresses.add(new Address(16, "PandlaVanka", "ctm", "PP", "INDIA"));
        addresses.add(new Address(17, "Lavangalu", "ctm", "QP", "ITALY"));
        addresses.add(new Address(18, "RandlaVanka", "Vayalpad", "RP", "CHINA"));
        addresses.add(new Address(19, "MiryalauGooda", "Pileru", "SP", "SOUTH AFRICA"));
        addresses.add(new Address(20, "KiranatkottuGadi", "Benguluru", "TP", "USA"));
        return addresses;
    }

    public List<Employee> generateEmployeesList() {

        List<Employee> employees = new ArrayList<>();

        List<String> sportsHobbies = generateSportsHobbies();
        List<String> supportHobbies = generateSupportingHobbies();
        List<String> negativeHobbies = generateNegativeHobbies();

        List<Department> departments = generateDepartmentList();
        List<Address> addresses = generateAddressList();

        employees.add(new Employee(1, 20000, 1, "Active", "BabaKalandhar", 26, "MALE", "CSE", sportsHobbies, departments.subList(6, 7), addresses.get(0), 2018));
        employees.add(new Employee(2, 30000, 2, "Inactive", "SharinAlavudeen", 23, "FEMALE", "IT", supportHobbies, departments.subList(7, 8), addresses.get(1), 2021));
        employees.add(new Employee(3, 40000, 3, "Active", "RukiyaAnjum", 21, "FEMALE", "ECE", negativeHobbies, departments.subList(4, 5), addresses.get(2), 2023));
        employees.add(new Employee(4, 25000, 4, "Active", "NikilaReddy", 28, "FEMALE", "EEE", sportsHobbies, departments.subList(5, 6), addresses.get(3), 2022));
        employees.add(new Employee(5, 38000, 5, "Inactive", "HarshithaReddy", 27, "FEMALE", "IT", supportHobbies, departments.subList(7, 8), addresses.get(4), 2021));
        employees.add(new Employee(6, 42000, 6, "Active", "RubyRani", 29, "FEMALE", "CSE", sportsHobbies, departments.subList(6, 7), addresses.get(5), 2020));
        employees.add(new Employee(7, 51000, 7, "Inactive", "AnkithaDave", 35, "FEMALE", "EEE", negativeHobbies, departments.subList(5, 6), addresses.get(6), 2010));
        employees.add(new Employee(8, 61000, 8, "Active", "ViratKohli", 40, "MALE", "IT", supportHobbies, departments.subList(7, 8), addresses.get(7), 2005));
        employees.add(new Employee(9, 28000, 9, "Active", "RajaKalandhar", 24, "MALE", "ECE", sportsHobbies, departments.subList(4, 5), addresses.get(8), 2015));
        employees.add(new Employee(10, 48000, 10, "Inactive", "KiranKumar", 30, "MALE", "MECHA", supportHobbies, departments.subList(2, 3), addresses.get(9), 2017));
        employees.add(new Employee(11, 52000, 11, "Active", "LakshmiPriya", 25, "FEMALE", "BIOTEC", sportsHobbies, departments.subList(3, 4), addresses.get(10), 2019));
        employees.add(new Employee(12, 39000, 12, "Inactive", "JhonCarter", 32, "MALE", "CHEMICAL", negativeHobbies, departments.subList(8, 9), addresses.get(11), 2016));
        employees.add(new Employee(13, 60000, 13, "Active", "AvinashRao", 33, "MALE", "CIVIL", supportHobbies, departments.subList(1, 2), addresses.get(12), 2013));
        employees.add(new Employee(14, 47000, 14, "Inactive", "SnehaShetty", 22, "FEMALE", "IT", supportHobbies, departments.subList(7, 8), addresses.get(13), 2021));
        employees.add(new Employee(15, 34000, 15, "Active", "RamGopal", 26, "MALE", "AGRICULTURE", sportsHobbies, departments.subList(10, 11), addresses.get(14), 2018));
        employees.add(new Employee(16, 45000, 16, "Inactive", "PriyaDarshini", 24, "FEMALE", "PHARMACY", supportHobbies, departments.subList(11, 12), addresses.get(15), 2022));
        employees.add(new Employee(17, 56000, 17, "Active", "SuryaNarayan", 29, "MALE", "ROBOTICS", sportsHobbies, departments.subList(12, 13), addresses.get(16), 2019));
        employees.add(new Employee(18, 33000, 18, "Inactive", "NishaMehra", 27, "FEMALE", "TEXTILE", supportHobbies, departments.subList(17, 18), addresses.get(17), 2020));
        employees.add(new Employee(19, 70000, 19, "Active", "ManojPillai", 31, "MALE", "AUTOMOBILE", negativeHobbies, departments.subList(18, 19), addresses.get(18), 2014));
        employees.add(new Employee(20, 64000, 20, "Active", "ZoyaAkhtar", 30, "FEMALE", "ENVIRONMENTAL", supportHobbies, departments.subList(19, 20), addresses.get(19), 2015));

        return employees;
    }


}

