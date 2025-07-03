package com.java.designPatterns.proxy;

class ProxyInternet implements Internet {
    private Internet internet = new RealInternet();
    private static final java.util.List<String> bannedSites = java.util.Arrays.asList("abc.com", "xyz.com");

    public void connectTo(String serverhost) {
        if (bannedSites.contains(serverhost.toLowerCase())) {
            System.out.println("Access Denied to " + serverhost);
        } else {
            internet.connectTo(serverhost);
        }
    }
}
