package com.example.shopapp.dto;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.shopapp.model.accommodation.Accommodation;
import com.example.shopapp.model.user.Guest;

import java.util.List;

public class GuestDTO implements Parcelable {
    private Long id;
    private String email;

    private String name;
    private String surname;
    private String phone;
    private String address;
    private List<Accommodation> favouriteAccommodations;
    private boolean turnOnNotification;

    public GuestDTO() {
    }

    public GuestDTO(Long id, String email, String name, String surname,boolean turnOnNotification, List<Accommodation> favouriteAccommodations, String phone, String address) {
        this();
        this.id=id;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.phone = phone;
        this.turnOnNotification = turnOnNotification;
        this.favouriteAccommodations = favouriteAccommodations;
    }

    public GuestDTO(Guest g) {
        this();
        this.id=g.getId();
        this.email = g.getEmail();
        this.name = g.getName();
        this.surname = g.getSurname();
        this.favouriteAccommodations= g.getFavouriteAccommodations();
        this.address = g.getAddress();
        this.phone = g.getPhone();
        this.turnOnNotification = g.isTurnOnNotification();
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isTurnOnNotification() {
        return turnOnNotification;
    }

    public void setTurnOnNotification(boolean turnOnNotification) {
        this.turnOnNotification = turnOnNotification;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

        protected GuestDTO(Parcel in) {
            this.email = in.readString();
            this.name = in.readString();
            this.surname = in.readString();
            this.phone = in.readString();
            this.address = in.readString();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                this.turnOnNotification = in.readBoolean();
            }else{
                this.turnOnNotification = Boolean.parseBoolean(in.readString());
            }
        }

        public final Creator<GuestDTO> CREATOR = new Creator<GuestDTO>() {
            @Override
            public GuestDTO createFromParcel(Parcel in) {

                return new GuestDTO(in);
            }

            @Override
            public GuestDTO[] newArray(int size) {
                return new GuestDTO[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.email);
            dest.writeString(this.name);
            dest.writeString(this.surname);
            dest.writeString(this.phone);
            dest.writeString(this.address);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                dest.writeBoolean(this.turnOnNotification);
            }else{
                dest.writeString(String.valueOf(this.turnOnNotification));
            }
        }


}
