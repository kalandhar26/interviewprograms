package com.ds.interviewquestions;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ConvertDate {

    public static void main(String[] args) {

        System.out.println(convertStringToISODate("1728988824983"));

    }

   public static String convertStringToISODate(String timestampStr) {
        try {
            // Convert the string to a long value
            long timestamp = Long.parseLong(timestampStr);

            // Convert long to Instant
            Instant instant = Instant.ofEpochMilli(timestamp);

            // Convert Instant to ZonedDateTime in UTC or any time zone you prefer
            ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(instant, ZoneId.of("UTC"));

            // Format the ZonedDateTime to ISO 8601 string format
            return zonedDateTime.format(DateTimeFormatter.ISO_INSTANT);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid timestamp string: " + timestampStr, e);
        }
    }
}
