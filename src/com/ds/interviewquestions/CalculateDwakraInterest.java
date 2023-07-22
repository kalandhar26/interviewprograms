package com.ds.interviewquestions;

public class CalculateDwakraInterest {

    public static void main(String[] args) {

        System.out.println(dwakraInterest(50000,12,0.5,4500));

    }

    public static double dwakraInterest(double loanAmount, int tenureMonths, double interestRate, double monthlyPayment){
        double currentLoanAmount= loanAmount;
        int NumberOfMonths=0;
        double finalInterestPaid=0;
     do{
            double interest = (currentLoanAmount*interestRate)/100;
            currentLoanAmount=currentLoanAmount-monthlyPayment;
           finalInterestPaid = finalInterestPaid+interest;
            NumberOfMonths++;
        }while (currentLoanAmount>0);

        return finalInterestPaid;
    }
}
