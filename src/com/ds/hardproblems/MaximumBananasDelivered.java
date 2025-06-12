package com.ds.hardproblems;

public class MaximumBananasDelivered {

    public static void main(String[] args) {

        System.out.println(maximumBananasDeliveredd(1000,4800,1200,1));

    }

    public static long maximumBananasDelivered(int distance, int totalbananas, int camelCapacity, int consumptionPerKm){

        int bananas = totalbananas;
        int distanceRemaining = distance;

        // Camel need to carry 3000 bananas and capacity is 1000. It makes 5 trips to carry 3000 bananas from point A to point B
        int optimumNumOfTrips1 = numberOfTrips(bananas,camelCapacity);
        int optimumDistance1 = camelCapacity/optimumNumOfTrips1;
        bananas = bananas - (optimumDistance1*optimumNumOfTrips1*consumptionPerKm);
        distanceRemaining = distanceRemaining-optimumDistance1;

        // Camel have only 2000 bananas now. It can 3 trips to carry 2000 bananas from point B to point C
        int optimumNumOfTrips2 = numberOfTrips(bananas,camelCapacity);
        int optimumDistance2 = camelCapacity/optimumNumOfTrips2;
        bananas -= (optimumDistance2*optimumNumOfTrips2*consumptionPerKm);
        distanceRemaining -= optimumDistance2;

        // Camel have only 1000 bananas left It can carry directly without return trips from point C to point D
        bananas -= distanceRemaining*consumptionPerKm;

        return Math.max(bananas,0);

    }

    public static long maximumBananasDeliveredd(int distance, int totalbananas, int camelCapacity, int consumptionPerKm){

        int bananasLeft = totalbananas;
        int distanceRemaining = distance;

        while (distanceRemaining > 0) {
            int trips = numberOfTrips(bananasLeft, camelCapacity);
            if (trips == 0) break;

            int segmentDistance = camelCapacity / (trips * consumptionPerKm);
            if (segmentDistance > distanceRemaining) {
                segmentDistance = distanceRemaining;
            }

            bananasLeft -= segmentDistance * trips * consumptionPerKm;
            distanceRemaining -= segmentDistance;
        }

        return Math.max(bananasLeft,0);

    }

    public static int numberOfTrips(int totalBananas, int camelCapacity){
        if(totalBananas==0 || camelCapacity==0)
            return 0;
        if (totalBananas <= camelCapacity) {
            return 1; // Only one trip needed
        } else {
            int trips = (totalBananas / camelCapacity) * 2 - 1;
            return trips;
        }
    }
}
