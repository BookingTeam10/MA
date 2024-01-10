package com.example.shopapp.model.user;

import java.io.Serializable;

public class Owner extends User implements Serializable {

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
    }
    public Long id() {
        return super.getId();
    }
}
