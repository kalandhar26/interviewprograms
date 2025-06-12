package com.ds.patterns;

public class TrianglePattern {
    public static void main(String[] args) {
        int depth = 4;
        leftTraingle(depth);
        System.out.println("=========");
        centeredTraingle(depth);
        System.out.println("=========");
        rightTraingle(depth);
    }

    public static void centeredTraingle(int depth) {
        for (int i = 1; i <= depth; i++) {
            for (int j = depth; j > i; j--) {
                System.out.print(" ");
            }
            for (int j = 1; j <= i; j++) {
                System.out.print("* ");
            }
            System.out.println();
        }
    }

    public static void leftTraingle(int depth) {
        for (int i = 1; i <= depth; i++) {
            for (int j = 1; j <= i; j++) {
                System.out.print("*");
            }
            System.out.println();
        }
    }

    public static void rightTraingle(int depth) {
        for (int i = 1; i <=depth; i++) {
            for (int j = 1; j <= depth-i; j++) {
                System.out.print(" ");
            }
            for (int j = 1; j <= i; j++) {
                System.out.print("*");
            }
            System.out.println(" ");
        }
    }
}
