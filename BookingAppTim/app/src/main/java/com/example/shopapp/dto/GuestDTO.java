package com.example.shopapp.dto;

import com.example.shopapp.model.accommodation.Accommodation;
import com.example.shopapp.model.user.Guest;

import java.util.List;

public class GuestDTO {
    private Long id;
    private String email;

    private String name;
    private String surname;
    private List<Accommodation> favouriteAccommodations;

    public GuestDTO() {
    }

    public GuestDTO(Long id, String email, String name, String surname, List<Accommodation> favouriteAccommodations) {
        this();
        this.id=id;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.favouriteAccommodations = favouriteAccommodations;
    }

    public GuestDTO(Guest g) {
        this();
        this.id=g.getId();
        this.email = g.getEmail();
        this.name = g.getName();
        this.surname = g.getSurname();
        this.favouriteAccommodations= g.getFavouriteAccommodations();
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public List<Accommodation> getFavouriteAccommodations() {
        return favouriteAccommodations;
    }

    public void setFavouriteAccommodations(List<Accommodation> favouriteAccommodations) {
        this.favouriteAccommodations = favouriteAccommodations;
    }

    @Override
    public String toString() {
        return "GuestDTO{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", favouriteAccommodations=" + favouriteAccommodations +
                '}';
    }
}
