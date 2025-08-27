package com.amazon;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;

public class AnalyticsProcessor {

    private final Deque<ActivityWindow> slidingWindows;
    private final int windowSize; // x time intervals
    private final int minActivities; // y threshold
    private final long windowDuration; // Duration of each window in milliseconds

    public AnalyticsProcessor(int windowSize, int minActivities, long windowDuration) {
        this.slidingWindows = new ConcurrentLinkedDeque<>();
        this.windowSize = windowSize;
        this.minActivities = minActivities;
        this.windowDuration = windowDuration;
    }

    public void processStreamData(Map<String, List<String>> streamData, long timestamp) {
        // Convert stream data to current window format
        ActivityWindow currentWindow = createWindowFromStream(streamData, timestamp);

        // Add to sliding window queue
        slidingWindows.addLast(currentWindow);

        // Maintain window size constraint
        if (slidingWindows.size() > windowSize) {
            slidingWindows.removeFirst();
        }
    }

    private ActivityWindow createWindowFromStream(Map<String, List<String>> data, long timestamp) {
        ActivityWindow window = new ActivityWindow(timestamp);

        for (Map.Entry<String, List<String>> entry : data.entrySet()) {
            String userId = entry.getKey();
            Set<String> uniqueActivities = new HashSet<>(entry.getValue());
            window.addUserActivities(userId, uniqueActivities);
        }

        return window;
    }

    public void processStreamDataa(Map<String, List<String>> streamData, long timestamp) {
        // Create and populate the current window
        ActivityWindow currentWindow = new ActivityWindow(timestamp);

        for (Map.Entry<String, List<String>> entry : streamData.entrySet()) {
            String userId = entry.getKey();
            Set<String> uniqueActivities = new HashSet<>(entry.getValue());
            currentWindow.addUserActivities(userId, uniqueActivities);
        }

        // Add to sliding window and maintain size constraint
        slidingWindows.addLast(currentWindow);

        // Remove oldest window if we exceed the window size
        if (slidingWindows.size() > windowSize) {
            slidingWindows.removeFirst();
        }

        // Optional: Clean up expired windows based on time
        cleanupExpiredWindows(timestamp);
    }

    private void cleanupExpiredWindows(long currentTime) {
        // Remove windows that are older than our time-based threshold
        while (!slidingWindows.isEmpty()) {
            ActivityWindow oldestWindow = slidingWindows.peekFirst();
            if (currentTime - oldestWindow.getWindowStartTime() > (windowSize * windowDuration)) {
                slidingWindows.removeFirst();
            } else {
                break;
            }
        }
    }

    public Map<String, Set<String>> getAggregatedActivities() {
        Map<String, Set<String>> aggregated = new HashMap<>();

        for (ActivityWindow window : slidingWindows) {
            Map<String, Set<String>> windowActivities = window.getUserActivities();

            for (Map.Entry<String, Set<String>> entry : windowActivities.entrySet()) {
                String userId = entry.getKey();
                Set<String> activities = entry.getValue();

                // Only include users who meet the threshold in THIS window
                if (activities.size() >= minActivities) {
                    aggregated.merge(userId, activities, (existing, newActivities) -> {
                        Set<String> combined = new HashSet<>(existing);
                        combined.addAll(newActivities);
                        return combined;
                    });
                }
            }
        }

        return aggregated;
    }

    // Additional method to get users with their activity counts
    public Map<String, Integer> getUserActivityCounts() {
        Map<String, Integer> counts = new HashMap<>();

        for (ActivityWindow window : slidingWindows) {
            Map<String, Set<String>> windowActivities = window.getUserActivities();

            for (Map.Entry<String, Set<String>> entry : windowActivities.entrySet()) {
                String userId = entry.getKey();
                int activityCount = entry.getValue().size();

                if (activityCount >= minActivities) {
                    counts.merge(userId, activityCount, Integer::sum);
                }
            }
        }

        return counts;
    }
}
