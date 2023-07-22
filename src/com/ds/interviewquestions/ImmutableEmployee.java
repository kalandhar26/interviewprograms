package com.ds.interviewquestions;

public final class ImmutableEmployee {

    private  final int id;
    private final String name;
    private final Address address;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    private ImmutableEmployee(int id, String name, Address address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }
}
