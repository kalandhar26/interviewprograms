package com.amazon;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ActivityWindow {

    private Map<String, Set<String>> userActivities; // user_id -> unique activities
    private long windowStartTime;
    private long windowEndTime;

    // Constructor with timestamp
    public ActivityWindow(long timestamp) {
        this.windowStartTime = timestamp;
        this.windowEndTime = timestamp; // Can be adjusted based on window duration
        this.userActivities = new ConcurrentHashMap<>();
    }

    // Method to add user activities
    public void addUserActivities(String userId, Set<String> activities) {
        userActivities.merge(userId, activities, (existing, newActivities) -> {
            Set<String> combined = new HashSet<>(existing);
            combined.addAll(newActivities);
            return combined;
        });
    }

    public Map<String, Set<String>> getUserActivities() {
        return userActivities;
    }

    public void setUserActivities(Map<String, Set<String>> userActivities) {
        this.userActivities = userActivities;
    }

    public long getWindowStartTime() {
        return windowStartTime;
    }

    public void setWindowStartTime(long windowStartTime) {
        this.windowStartTime = windowStartTime;
    }

    public long getWindowEndTime() {
        return windowEndTime;
    }

    public void setWindowEndTime(long windowEndTime) {
        this.windowEndTime = windowEndTime;
    }
}
