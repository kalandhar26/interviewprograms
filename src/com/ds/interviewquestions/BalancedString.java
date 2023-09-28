package com.ds.interviewquestions;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

public class BalancedString {

    public static void main(String[] args) {

        System.out.println(isBalanced("{[]()({})[][({})]}"));

    }


    public static boolean isBalanced(String str){
        char[] charArray = str.toCharArray();

        Deque<Character> stack = new ArrayDeque<>();

        for(char c : charArray){
            if( c == '(' || c =='[' || c == '{'){
                stack.push(c);
            }else if( c== ')' || c ==']' || c =='}'){
                if(stack.isEmpty())
                    return false;

                char top = stack.pop();

                if(c== ')' && top != '(' || c ==']' && top != '[' || c =='}' && top != '{')
                    return false;
            }
        }
        return stack.isEmpty();
    }
}
