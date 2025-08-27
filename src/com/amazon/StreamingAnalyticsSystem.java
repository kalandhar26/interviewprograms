package com.amazon;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class StreamingAnalyticsSystem {
    public static void main(String[] args) {
        // Initialize processor with window size 5, min 2 activities, 1-minute windows
        AnalyticsProcessor processor = new AnalyticsProcessor(5, 2, 60000);

        // Sample stream data
        Map<String, List<String>> streamData = Map.of(
                "user_123", List.of("click", "view", "purchase"),
                "user_456", List.of("view", "click"),
                "user_786", List.of("purchase", "view", "click", "share")
        );

        // Process the stream with current timestamp
        processor.processStreamDataa(streamData, System.currentTimeMillis());

        // Get aggregated results
        Map<String, Set<String>> results = processor.getAggregatedActivities();
        System.out.println("Aggregated Activities: " + results);

        System.out.println(processor.getUserActivityCounts());
    }
}