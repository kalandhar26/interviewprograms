package com.java;

import java.util.ArrayDeque;
import java.util.Deque;

public class Test {

    public static void main(String[] args) {
//        int[] array1 = {1,2,-3,-4,7,8,9,-4,-2,0};
//
//        List<Integer> list = Arrays.stream(array1).filter(number -> number < 0).boxed().collect(Collectors.toList());
//
//        for(Integer x : list)
//            System.out.println(x);

        String input ="(){}[]";

        char[] array = input.toCharArray();

        Deque<Character> stack = new ArrayDeque<>();

        for(char c : array){
            if(c == '(' || c == '{' || c =='['){
                stack.push(c);
            }else if(c =='}' || c == ']' || c ==')'){
                if(stack.isEmpty())
                    System.out.println("Not-Balanced");

                char top = stack.pop();

                if(c == '}' && top != '{' || c == ']' && top != '['||c == ')' && top != '(' )
                    System.out.println("Not-Balanced");
            }else{
                if(stack.isEmpty())
                    System.out.println("Balanced");
            }
        }
    }
}
