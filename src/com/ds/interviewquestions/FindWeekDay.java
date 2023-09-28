package com.ds.interviewquestions;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class FindWeekDay {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter date in YYYY-MM-DD Format");
        String input = scanner.nextLine();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(input,formatter);

        System.out.println(date.getDayOfWeek());
    }
}
