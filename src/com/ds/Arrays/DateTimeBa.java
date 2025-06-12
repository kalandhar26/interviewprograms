package com.ds.Arrays;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;


public class DateTimeBa {

    public static void main(String[] args) {
        System.out.println(generateUniqueRequestId());
        String input = "789";
        System.out.println(formatAmount1(input));
        generateUniqueRequestId1(32);

    }

    public static String getTime(String dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss:'Z'");
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("GMT"));
        return now.format(formatter);

    }

    private static String generateUniqueRequestId() {
        String prefix = "AUS-NPP-";
        int maxLength = 35;
        int randomLength = maxLength - prefix.length();
        String randomString = RandomStringUtils.randomAlphabetic(randomLength).toUpperCase();
        String requestId = prefix + randomString;
        System.out.println(requestId);
        return requestId;
    }

    public static String formatAmount(String amount) {
        double doubleAmount = Double.parseDouble(amount);
        return String.format("%.2f", doubleAmount);
    }

    public static BigDecimal formatAmount1(String amount) {

        BigDecimal bigDecimalAmount = new BigDecimal(amount);
        return bigDecimalAmount.setScale(2, RoundingMode.HALF_UP);
    }

    public static void generateUniqueRequestId1(int length) {
        System.out.println(UUID.randomUUID().toString().toUpperCase().replace("-", "").substring(0, length));
        System.out.println(UUID.randomUUID().toString().toUpperCase().toUpperCase().substring(0, length));
    }

}
