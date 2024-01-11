package com.example.shopapp.model.accommodation;

import java.util.List;

public class Location {
    private Long id;
    private String country;
    private String city;
    private String street;
    private int number;
    private List<Accommodation> accommodations;

    public Location(Long id, String country, String city, String street, int number) {
        this.id = id;
        this.country = country;
        this.city = city;
        this.street = street;
        this.number = number;
    }
    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", number=" + number +
                ", accommodations=" + accommodations +
                '}';
    }
}
