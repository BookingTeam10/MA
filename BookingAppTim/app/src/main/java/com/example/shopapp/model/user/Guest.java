package com.example.shopapp.model.user;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.shopapp.dto.GuestDTO;
import com.example.shopapp.model.accommodation.Accommodation;

import java.util.ArrayList;
import java.util.List;

public class Guest extends User implements Parcelable {
    private int numberCanceledReservation;

    private boolean turnOnNotification;
    private List<Accommodation> favouriteAccommodations;

    public Guest(Long id){
        super.setId(id);
    }

    public Guest(Long id,String email, String password, String name, String surname, String phone, String address,boolean rep, boolean blocked) {
        super(id,email, password, name, surname, phone, address,rep, blocked);
        this.numberCanceledReservation=0;
        this.turnOnNotification=true;
        this.favouriteAccommodations = new ArrayList<>();
    }
    public Guest(Long id,String email, String password, String name, String surname, String phone, String address,boolean rep, boolean blocked, int numberCanceledReservation,boolean turnOnNotification) {
        super(id,email, password, name, surname, phone, address,rep,blocked);
        this.numberCanceledReservation = numberCanceledReservation;
        this.turnOnNotification=turnOnNotification;
        this.favouriteAccommodations = new ArrayList<>();
    }
    public Guest(Long id,String email, String password, String name, String surname, String phone, String address,boolean rep, boolean blocked, int numberCanceledReservation,boolean turnOnNotification,List<Accommodation> favouriteAccommodations) {
        super(id,email, password, name, surname, phone, address,rep,blocked);
        this.numberCanceledReservation = numberCanceledReservation;
        this.turnOnNotification=turnOnNotification;
        this.favouriteAccommodations = favouriteAccommodations;
    }

    public Guest(Long id, String email, String password, String name, String surname, String phone, String address) {
        super(id,email, password, name, surname, phone, address);
        this.numberCanceledReservation=0;
        this.turnOnNotification=true;
        this.favouriteAccommodations = new ArrayList<>();
    }

    public Guest(Long id,String email, String password, String name, String surname, String phone, String address, int numberCanceledReservation,boolean turnOnNotification) {
        super(id,email, password, name, surname, phone, address);
        this.numberCanceledReservation = numberCanceledReservation;
        this.turnOnNotification=turnOnNotification;
        this.favouriteAccommodations = new ArrayList<>();
    }
    public Guest() {}
    public Guest(Guest g) {
        super(g.getId(),g.getEmail(), g.getPassword(), g.getName(), g.getSurname(), g.getPhone(), g.getAddress(),g.isReported(),g.isBlocked());
        this.numberCanceledReservation=0;
        this.turnOnNotification=true;
        this.favouriteAccommodations = new ArrayList<>();
    }
    public int getNumberCanceledReservation() {
        return numberCanceledReservation;
    }
    public void setNumberCanceledReservation(int numberCanceledReservation) {this.numberCanceledReservation = numberCanceledReservation;}
    public boolean isTurnOnNotification() {return turnOnNotification;}
    public void setTurnOnNotification(boolean turnOnNotification) {this.turnOnNotification = turnOnNotification;}
    public List<Accommodation> getFavouriteAccommodations() {return favouriteAccommodations;}
    public void setFavouriteAccommodations(List<Accommodation> favouriteAccommodations) {this.favouriteAccommodations = favouriteAccommodations;}
    public void copyValues(Guest guestForUpdate) {
        this.setEmail(guestForUpdate.getEmail());
        this.setPassword(guestForUpdate.getPassword());
        this.setName(guestForUpdate.getName());
        this.setSurname(guestForUpdate.getSurname());
        this.setPhone(guestForUpdate.getPhone());
        this.setAddress(guestForUpdate.getAddress());
        this.setNumberCanceledReservation(guestForUpdate.getNumberCanceledReservation());
        this.setTurnOnNotification(guestForUpdate.isTurnOnNotification());
        this.setFavouriteAccommodations(guestForUpdate.getFavouriteAccommodations());
    }
    @Override
    public String toString() {
        return "Guest{" +
                "numberCanceledReservation=" + numberCanceledReservation +
                ", turnOnNotification=" + turnOnNotification +
                ", favouriteAccommodations=" + favouriteAccommodations +
                '}';
    }


    protected Guest(Parcel in) {
        this.setEmail(in.readString());
        this.setName(in.readString());
        this.setSurname(in.readString());
        this.setPhone(in.readString());
        this.setAddress(in.readString());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            this.turnOnNotification = in.readBoolean();
        }else{
            this.turnOnNotification = Boolean.parseBoolean(in.readString());
        }
    }

    public final Creator<Guest> CREATOR = new Creator<Guest>() {
        @Override
        public Guest createFromParcel(Parcel in) {

            return new Guest(in);
        }

        @Override
        public Guest[] newArray(int size) {
            return new Guest[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.getEmail());
        dest.writeString(this.getName());
        dest.writeString(this.getSurname());
        dest.writeString(this.getPhone());
        dest.writeString(this.getAddress());
        dest.writeString(this.getPassword());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            dest.writeBoolean(this.turnOnNotification);
        }else{
            dest.writeString(String.valueOf(this.turnOnNotification));
        }
    }

}
