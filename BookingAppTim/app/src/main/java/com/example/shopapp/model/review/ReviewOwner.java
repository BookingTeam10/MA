package com.example.shopapp.model.review;

import com.example.shopapp.enums.ReviewStatus;
import com.example.shopapp.model.user.Guest;
import com.example.shopapp.model.user.Owner;

import java.io.Serializable;
import java.util.Date;

public class ReviewOwner implements Serializable {

    private Long id;
    private double rate;

    private String comment;

    private ReviewStatus status;
    private Date commentDate;
    private Owner owner;

    private Guest guest;

    private boolean is_reported;

    public ReviewOwner() {
    }

    public ReviewOwner(double rate, String comment, ReviewStatus status) {
        this.rate = rate;
        this.comment = comment;
        this.status = status;
    }

    public ReviewOwner(Long id, double rate, String comment, ReviewStatus status, Date commentDate, Owner owner, Guest guest, boolean is_reported) {
        this.id = id;
        this.rate = rate;
        this.comment = comment;
        this.status = status;
        this.commentDate = commentDate;
        this.owner = owner;
        this.guest = guest;
        this.is_reported = is_reported;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public ReviewStatus getStatus() {
        return status;
    }

    public void setStatus(ReviewStatus status) {
        this.status = status;
    }

    public Date getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public boolean isIs_reported() {
        return is_reported;
    }

    public void setIs_reported(boolean is_reported) {
        this.is_reported = is_reported;
    }

    @Override
    public String toString() {
        return "ReviewOwner{" +
                "id=" + id +
                ", rate=" + rate +
                ", comment='" + comment + '\'' +
                ", status=" + status +
                ", commentDate=" + commentDate +
                ", owner=" + owner +
                ", guest=" + guest +
                ", is_reported=" + is_reported +
                '}';
    }

}
