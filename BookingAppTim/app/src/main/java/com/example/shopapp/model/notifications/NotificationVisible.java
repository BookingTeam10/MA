package com.example.shopapp.model.notifications;

import com.example.shopapp.model.user.Guest;
import com.example.shopapp.model.user.Owner;

import java.io.Serializable;

public class NotificationVisible implements Serializable {
    private Long id;
    private String text;
    private Guest guest;
    private Owner owner;
    private String userRate;

    public NotificationVisible() {
    }

    public NotificationVisible(Long id, String text, Guest guest, Owner owner, String userRate) {
        this.id = id;
        this.text = text;
        this.guest = guest;
        this.owner = owner;
        this.userRate = userRate;
    }

    public Long getId() {
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

    public String getUserRate() {
        return userRate;
    }

    public void setUserRate(String userRate) {
        this.userRate = userRate;
    }
    @Override
    public String toString() {
        return "NotificationVisible{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", guest=" + guest +
                ", owner=" + owner +
                ", userRate='" + userRate + '\'' +
                '}';
    }
}
