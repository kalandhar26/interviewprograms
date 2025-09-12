package com.ds.interviewquestions;

public class Address {


    private Integer id;

    private String street;

    private String city;


    private String state;

    private String country;

    public Address() {
    }


        public Address(Integer id, String street, String city, String state, String country) {
            this.id = id;
            this.street = street;
            this.city = city;
            this.state = state;
            this.country = country;
        }

    public Address(Address address) {
        this.id = address.id;
        this.street = address.street;
        this.city = address.city;
        this.state = address.state;
        this.country = address.country;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                '}';
    }





}
