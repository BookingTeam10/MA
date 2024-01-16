package com.example.shopapp.model.user;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Owner extends User implements Serializable, Parcelable {
    private double totalPrice;
    private double rating;

    public void setCreatedNotification(boolean createdNotification) {
        this.createdNotification = createdNotification;
    }

    public void setRateMeNotification(boolean rateMeNotification) {
        this.rateMeNotification = rateMeNotification;
    }

    public void setCancelledNotification(boolean cancelledNotification) {
        this.cancelledNotification = cancelledNotification;
    }

    public void setRateAccomodationNotification(boolean rateAccommodationNotification) {
        this.rateAccommodationNotification = rateAccommodationNotification;
    }

    public void setRateAccommodationNotification(boolean rateAccommodationNotification) {
        this.rateAccommodationNotification = rateAccommodationNotification;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public boolean isCreatedNotification() {
        return createdNotification;
    }

    public boolean isRateMeNotification() {
        return rateMeNotification;
    }

    public boolean isCancelledNotification() {
        return cancelledNotification;
    }

    public boolean isRateAccommodationNotification() {
        return rateAccommodationNotification;
    }

    private boolean createdNotification;
    private boolean rateMeNotification;
    private boolean cancelledNotification;
    private boolean rateAccommodationNotification;

    public Owner(Long id,String email, String password, String name, String surname, String phone, String address,boolean rep, boolean blocked) {
        super(id,email, password, name, surname, phone, address, rep,blocked);
        this.totalPrice=0;
        this.rating=0;
        this.createdNotification=true;
        this.rateMeNotification=true;
        this.cancelledNotification=true;
        this.rateAccommodationNotification=true;
    }

    public Owner(Long id,String email, String password, String name, String surname, String phone, String address,boolean rep, boolean blocked,double totalPrice,double rating, boolean createdNotification, boolean rateMeNotification, boolean cancelledNotification, boolean rateAccomodationNotification) {
        super(id,email, password, name, surname, phone, address,rep, blocked);
        this.totalPrice=0;
        this.rating=0;
        this.createdNotification=createdNotification;
        this.rateMeNotification=rateMeNotification;
        this.cancelledNotification=cancelledNotification;
        this.rateAccommodationNotification=rateAccomodationNotification;
    }

    public Owner() {
    }

    public Owner(Long id) {
        super(id);
    }

    public double getRating() {
        return rating;
    }

    public Owner(Owner owner) {
        super(owner.getId(), owner.getEmail(), owner.getPassword(), owner.getName(), owner.getSurname(), owner.getPhone(), owner.getAddress(),owner.isReported(), owner.isBlocked());
        this.totalPrice=0;
        this.rating=0;
        this.createdNotification=true;
        this.rateMeNotification=true;
        this.cancelledNotification=true;
        this.rateAccommodationNotification=true;
    }

    @Override
    public String toString() {
        return "Owner{" +
                "email="+ super.getEmail()+
                "totalPrice=" + totalPrice +
                ", rating=" + rating +
                ", turnOnNotification1=" + createdNotification +
                ", rate me=" + rateMeNotification +
                ", cancelled=" + cancelledNotification +
                ", rate accommodation=" + rateAccommodationNotification +
                '}';
    }

    public void copyValues(Owner ownerForUpdate) {
        this.setEmail(ownerForUpdate.getEmail());
        this.setPassword(ownerForUpdate.getPassword());
        this.setName(ownerForUpdate.getName());
        this.setSurname(ownerForUpdate.getSurname());
        this.setPhone(ownerForUpdate.getPhone());
        this.setAddress(ownerForUpdate.getAddress());
        this.setBlocked(ownerForUpdate.isBlocked());
    }
    public Long id() {
        return super.getId();
    }


    protected Owner(Parcel in) {
        this.setEmail(in.readString());
        this.setName(in.readString());
        this.setSurname(in.readString());
        this.setPhone(in.readString());
        this.setAddress(in.readString());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            this.cancelledNotification = in.readBoolean();
            this.createdNotification = in.readBoolean();
            this.rateMeNotification = in.readBoolean();
            this.rateAccommodationNotification = in.readBoolean();
        }else{
            this.cancelledNotification = Boolean.parseBoolean(in.readString());
            this.createdNotification = Boolean.parseBoolean(in.readString());
            this.rateMeNotification = Boolean.parseBoolean(in.readString());
            this.rateAccommodationNotification = Boolean.parseBoolean(in.readString());
        }
    }

    public final Creator<Owner> CREATOR = new Creator<Owner>() {
        @Override
        public Owner createFromParcel(Parcel in) {

            return new Owner(in);
        }

        @Override
        public Owner[] newArray(int size) {
            return new Owner[size];
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
            dest.writeBoolean(this.cancelledNotification);
            dest.writeBoolean(this.rateAccommodationNotification);
            dest.writeBoolean(this.rateMeNotification);
            dest.writeBoolean(this.createdNotification);
        }else{
            dest.writeString(String.valueOf(this.cancelledNotification));
            dest.writeString(String.valueOf(this.rateAccommodationNotification));
            dest.writeString(String.valueOf(this.rateMeNotification));
            dest.writeString(String.valueOf(this.createdNotification));
        }
    }

}
