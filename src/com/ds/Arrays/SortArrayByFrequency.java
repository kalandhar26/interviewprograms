package com.ds.Arrays;

import java.util.*;

public class SortArrayByFrequency {

    public static void main(String[] args) {
        // [3,1,1,2,2,2]
        int[] array = {1,1,2,2,2,3};
        //[1,3,3,2,2]
        int[] array1 = {2,3,1,3,2};
        // [5,-1,4,4,-6,-6,1,1,1]
        int[] array2 = {-1,1,-6,4,5,-6,1,4,1};

        int[] output = frequnecnySort(array);
        int[] output1= frequnecnySort(array1);
        int[] output2 = frequnecnySort(array2);

        int[] output3 = frequencySort(array);
        int[] output4= frequencySort(array1);
        int[] output5 = frequencySort(array2);

        printSortedArray(output);
        printSortedArray(output1);
        printSortedArray(output2);
        printSortedArray(output3);
        printSortedArray(output4);
        printSortedArray(output5);



    }

    public static int[] frequnecnySort(int[] nums) {
        int[][] freqArray = new int[201][2];

        for (int num : nums) {
            freqArray[num + 100][1]++;
        }

        for (int i = 0; i < 201; i++) {
            freqArray[i][0] = i - 100;
        }

        Arrays.sort(freqArray, (a, b) ->{
            if(a[1]==b[1]){
                return Integer.compare(b[0],a[0]);
            }else{
                return Integer.compare(a[1],b[1]);
            }
        });

        int idx=0;
        for(int i=0;i<201;i++){
            for(int j=0;j<freqArray[i][1];j++){
                nums[idx++] = freqArray[i][0];
            }
        }

        return nums;
    }

    public static int[] frequencySort(int[] nums) {
        // Count frequencies using a HashMap
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (int num : nums) {
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
        }

        // Sort the array based on frequency and value
        return Arrays.stream(nums)
                .boxed()
                .sorted((a, b) -> {
                    int freqCompare = Integer.compare(frequencyMap.get(a), frequencyMap.get(b));
                    return freqCompare != 0 ? freqCompare : Integer.compare(b, a); // Sort by value if frequencies are equal
                })
                .mapToInt(Integer::intValue)
                .toArray();
    }

    private static void printSortedArray(int[] sortedArray) {
        System.out.println(Arrays.toString(sortedArray));
    }
}
