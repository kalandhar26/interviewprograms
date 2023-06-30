package com.ds.searching;

public class Squareroot {

    public static void main(String[] args) {
        System.out.println(squareRootBS(15));
    }


    // Naive
    private static int squareRoot(int x){
        int result =1;

        while(result*result <=x)
            result++;

        return result-1;

    }

    // Efficeint ( log n)

    private static int squareRootBS(int x){
        int low=1, high = x, result=-1;

        while(low<=high){
            int mid = (low+high)/2;
            int midsq = mid*mid;

            if(midsq == x){
                return mid;
            } else if (midsq >x) {
                high = mid-1;
            }else {
                low=mid+1;
                result = mid;
            }
        }

        return result;
    }
}
