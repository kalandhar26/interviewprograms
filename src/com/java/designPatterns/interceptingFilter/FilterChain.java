package com.java.designPatterns.interceptingFilter;

import java.util.ArrayList;
import java.util.List;

class FilterChain {
    private List<Filter> filters = new ArrayList<>();

    public void addFilter(Filter filter) {
        filters.add(filter);
    }

    public void execute(String request) {
        for (Filter filter : filters) {
            filter.execute(request);
        }
    }
}