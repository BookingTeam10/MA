package com.example.shopapp.model.notifications;

import com.example.shopapp.enums.NotificationStatus;
import com.example.shopapp.model.user.Guest;
import com.example.shopapp.model.user.Owner;

import java.util.Date;

public class NotificationUserVisible {
    private Long id;
    private String text;
    private NotificationStatus status;
    private Guest guest;
    private Owner owner;
    private String sentDate;
    private String userRate;

    public NotificationUserVisible(Long id, String text, NotificationStatus status, Guest guest, Owner owner, String sentDate, String userRate) {
        this.id = id;
        this.text = text;
        this.status = status;
        this.guest = guest;
        this.owner = owner;
        this.sentDate = sentDate;
        this.userRate = userRate;
    }
    public NotificationUserVisible() {

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
    public String getSentDate() {
        return sentDate;
    }
    public void setSentDate(String sentDate) {
        this.sentDate = sentDate;
    }
    public String getUserRate() {
        return userRate;
    }

    public void setUserRate(String userRate) {
        this.userRate = userRate;
    }

    @Override
    public String toString() {
        return "NotificationUserVisible{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", status=" + status +
                ", guest=" + guest +
                ", owner=" + owner +
                ", sentDate=" + sentDate +
                ", userRate='" + userRate + '\'' +
                '}';
    }
}
