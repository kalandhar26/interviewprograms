package com.ds.interviewquestions;

public class RemoveAdjacentCharacters {

    public static String removeAdjacentDuplicates(String inputString){
        if(inputString==null || inputString.isEmpty()|| inputString.isBlank()){
            return "";
        }

        StringBuilder result = new StringBuilder(inputString);
        boolean hasAdjacentDuplicates;

        do {
            hasAdjacentDuplicates = false;
            int i = 0;
            while (i < result.length() - 1) {
                if (result.charAt(i) == result.charAt(i + 1)) {
                    result.delete(i, i + 2);
                    hasAdjacentDuplicates = true;
                } else {
                    i++;
                }
            }
        } while (hasAdjacentDuplicates);

        return result.toString();
    }


     public static void main(String[] args) {

        String inputString = "cnncbbcddffcabc";
        String outputString = removeAdjacentDuplicates(inputString);
        System.out.println("Input: " + inputString);
        System.out.println("Output: " + outputString);

    }
}
