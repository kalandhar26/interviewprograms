package com.ds.interviewquestions;

import java.math.BigInteger;

public class InvestmenstGoal {

    public static void main(String[] args) {

        calculateCapital(1000000, 36, 2030);
        calculateCapitalPerMonth(1000000, 3.05, 2030);
    }

    public static void calculateCapital(double initialInvestment, double roi, int targetYear) {
        int currentYear = java.time.Year.now().getValue();

        for (int i = currentYear; i <= targetYear; i++) {
            System.out.println(i + " June: Your Capital is " + initialInvestment);
            initialInvestment += (initialInvestment * roi) / 100;
        }
    }

    public static void calculateCapitalPerMonth(double initialInvestment, double roi, int targetYear) {
        int currentYear = java.time.Year.now().getValue();
        double capitalUsed = 0;
        for (int i = currentYear; i <= targetYear; i++) {
            System.out.println(currentYear + " Captial is " + initialInvestment);
            for (int j = 1; j < 12; j++) {
                capitalUsed = (initialInvestment / 125000);
                double profit = ((capitalUsed * 125000 *roi))/100;
                initialInvestment = initialInvestment+profit;
            }
            currentYear++;
        }
    }
}
