package com.example.shopapp.model.notifications;


import com.example.shopapp.enums.NotificationStatus;
import com.example.shopapp.model.user.Owner;
import com.example.shopapp.model.user.Guest;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;


public class Notification implements Serializable {

    private Long id;
    private String text;
    private NotificationStatus status;
    private Guest guest;
    private Owner owner;
    private Date sentDate;

    public Notification() {
    }

    public Notification(String text, NotificationStatus status, Guest guest, Owner owner, Date sentDate) {
        this.text = text;
        this.status = status;
        this.guest = guest;
        this.owner = owner;
        this.sentDate = sentDate;
    }
    public Long getId() {
        if(id == null){return 0L;}
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public NotificationStatus getStatus() {
        return status;
    }

    public void setStatus(NotificationStatus status) {
        this.status = status;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Date getSentDate() {
        return sentDate;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }

    public void copyValues(Notification notification) {
    }
}

