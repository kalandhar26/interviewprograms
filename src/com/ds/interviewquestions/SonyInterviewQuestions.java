package com.ds.interviewquestions;

public class SonyInterviewQuestions {

    public static int objCount=0;
    public SonyInterviewQuestions() {
        objCount++;
    }

    // For a Single Class we can create Multiple Objects.
    // How do I get Number of Objects are Created.
    public static void main(String[] args) {
        SonyInterviewQuestions s1 = new SonyInterviewQuestions();
        SonyInterviewQuestions s2 = new SonyInterviewQuestions();

        System.out.println(getNumberOfObjectCreated());
    }

    public static int getNumberOfObjectCreated(){
        return objCount;
    }
}
