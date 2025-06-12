package com.ds.Arrays;

public class RainWaterTrapping {

    public static void main(String[] args) {
        int[] height = {3,2,1,0,1,3,2,0,4};
        System.out.println("Total water trapped: " + trap(height));
        System.out.println("Calculate water trapped: " + calculateWaterTrapped(height));
    }

    public static int trap(int[] height) {
        if (height == null || height.length == 0) {
            return 0;
        }

        int left = 0;
        int right = height.length - 1;
        int leftMax = height[left];
        int rightMax = height[right];
        int waterTrapped = 0;

        while (left < right) {
            if (leftMax <= rightMax) {
                left++;
                leftMax = Math.max(leftMax, height[left]);
                waterTrapped += Math.max(0, leftMax - height[left]);
            } else {
                right--;
                rightMax = Math.max(rightMax, height[right]);
                waterTrapped += Math.max(0, rightMax - height[right]);
            }
        }

        return waterTrapped;
    }


    public static int calculateWaterTrapped(int[] heights) {
        if (heights == null || heights.length == 0) {
            return 0; // No heights means no water can be trapped
        }

        int left = 0;
        int right = heights.length - 1;
        int leftMax = 0;
        int rightMax = 0;
        int waterTrapped = 0;

        while (left < right) {
            if (heights[left] <= heights[right]) {
                if (heights[left] >= leftMax) {
                    leftMax = heights[left]; // Update leftMax
                } else {
                    waterTrapped += leftMax - heights[left]; // Calculate water trapped
                }
                left++; // Move left pointer to the right
            } else {
                if (heights[right] >= rightMax) {
                    rightMax = heights[right]; // Update rightMax
                } else {
                    waterTrapped += rightMax - heights[right]; // Calculate water trapped
                }
                right--; // Move right pointer to the left
            }
        }

        return waterTrapped; // Return the total water trapped
    }
}
