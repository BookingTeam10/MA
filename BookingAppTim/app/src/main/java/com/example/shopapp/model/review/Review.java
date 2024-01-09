package com.example.shopapp.model.review;

import com.example.shopapp.enums.ReviewStatus;
import com.example.shopapp.model.reservation.Reservation;

import java.io.Serializable;

public class Review implements Serializable {

    private long id;

    private double rate;
    private String comment;
    private ReviewStatus status;

    private Reservation reservation;

    public Review() {
    }

    public Review(double rate, String comment, ReviewStatus status) {
        this.rate = rate;
        this.comment = comment;
        this.status = status;
    }

    public long getId() {
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

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }


    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", rate=" + rate +
                ", comment='" + comment + '\'' +
                ", status=" + status +
                '}';
    }
}
