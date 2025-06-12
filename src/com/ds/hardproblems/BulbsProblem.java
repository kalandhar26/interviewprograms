package com.ds.hardproblems;

public class BulbsProblem {

    public static void main(String[] args) {

        System.out.println(bulbsSwitchedOn(1000,100));

    }

    public static  int bulbsSwitchedOn(int totalBulbs, int totalMembers){
        // All the bulbs are switched off initially.
        // Odd Numbers of flips will On the bulb and Even number of flips Switched Off the bulb.
        // All the Numbers will have even number of Factors Except Perfect Squares.
        int output = 1;
        int count=1;
        while(output<=totalBulbs){
            count++;
            output = count*count;
            System.out.println(output);
        }
        return count-1;
    }
}
