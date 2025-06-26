package com.ds.interviewquestions;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateFormatter {
    public static void main(String[] args) {
        String inputDate = "20240101";
        String formattedDate = formatDate(inputDate);
        System.out.println(formattedDate); // Output: 2024-01-01
    }

    public static String formatDate(String inputDate) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate date = LocalDate.parse(inputDate, inputFormatter);
        return date.format(outputFormatter);
    }
}


