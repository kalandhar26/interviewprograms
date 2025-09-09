package com.ds.Arrays;

import java.util.Collections;
import java.util.PriorityQueue;

public class KthSmallestElement {

    public static void main(String[] args) {
        int[] array = {1, 3, 4, 5, 7, 6, 9, 2,8};
        System.out.println(kthSmallestElement(array, 3));
        System.out.println(kthLargestElement(array, 3));
    }

    public static int kthSmallestElement(int[] array, int k) {
        if (array == null || array.length < k || k <= 0) {
            throw new IllegalArgumentException("Invalid input");
        }

        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

        for (int num : array) {
            maxHeap.offer(num);
            if (maxHeap.size() > k)
                maxHeap.poll();
        }

        return maxHeap.isEmpty() ? -1 : maxHeap.peek();
    }


    public static int kthLargestElement(int[] array, int k) {
        if (array == null || array.length < k || k <= 0) {
            throw new IllegalArgumentException("Invalid input");
        }

        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        for (int num : array) {
            minHeap.offer(num);
            if (minHeap.size() > k)
                minHeap.poll();
        }

        return minHeap.isEmpty() ? -1 : minHeap.peek();
    }
}
