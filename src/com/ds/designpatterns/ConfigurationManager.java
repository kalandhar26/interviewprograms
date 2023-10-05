package com.ds.designpatterns;


/*
used the Singleton pattern to ensure that certain resources or components within your microservices
are instantiated only once.

Ex: If you have a configuration manager or a cache manager that needs to be a single,
shared instance across your microservices, you can implement it as a Singleton.
 */
public class ConfigurationManager {
    private static ConfigurationManager instance;

    private ConfigurationManager() {
        // Private constructor to prevent instantiation.
    }

    public static ConfigurationManager getInstance() {
        if (instance == null) {
            instance = new ConfigurationManager();
        }
        return instance;
    }

    // Other configuration-related methods...
}
