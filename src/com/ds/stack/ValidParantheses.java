package com.ds.stack;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class ValidParantheses {

    public static void main(String[] args) {
        String input = "[{()}]";
        System.out.println(isValidParenthesis(input));
        System.out.println(isValidParanthes(input));
    }

    public static boolean isValidParenthesis(String input) {
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < input.length(); i++) {
            char current = input.charAt(i);
            if (current == '[' || current == '{' || current == '(') {
                stack.push(current);
            } else {
                if (!stack.empty() && (stack.peek() == '(' && input.charAt(i) == ')' || stack.peek() == '{' && input.charAt(i) == '}' || stack.peek() == '[' && input.charAt(i) == ']')) {
                    stack.pop();
                } else {
                    return false;
                }
            }
        }
        return stack.empty();
    }

    public static int isValidParanthesisBy(String input) {
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < input.length(); i++) {
            char current = input.charAt(i);
            if (current == '[' || current == '{' || current == '(') {
                stack.push(current);
            } else {
                if (!stack.empty() && (stack.peek() == '(' && input.charAt(i) == ')' || stack.peek() == '{' && input.charAt(i) == '}' || stack.peek() == '[' && input.charAt(i) == ']')) {
                    stack.pop();
                } else {
                    return 0;
                }
            }
        }
        return stack.size();
    }

    public static boolean isValidParanthes(String input) {
        Stack<Character> stack = new Stack<>();
        Map<Character, Character> matchingPairs = getMatchingPairs();

        for (int i = 0; i < input.length(); i++) {
            char currentCharacter = input.charAt(i);
            if (matchingPairs.containsKey(currentCharacter)) {
                stack.push(currentCharacter);
            } else {
                if (!stack.empty() && matchingPairs.get(stack.peek()) == currentCharacter) {
                    stack.pop();
                } else {
                    return false;
                }
            }
        }

        return stack.empty();
    }

    public static Map<Character, Character> getMatchingPairs() {
        Map<Character, Character> matchingPairs = new HashMap<>();
        matchingPairs.put('(', ')');
        matchingPairs.put('{', '}');
        matchingPairs.put('[', ']');

        return matchingPairs;
    }
}
