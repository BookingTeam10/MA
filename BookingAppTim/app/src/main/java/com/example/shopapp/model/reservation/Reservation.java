package com.example.shopapp.model.reservation;


import com.example.shopapp.dto.ReservationDTO;
import com.example.shopapp.enums.ReservationStatus;
import com.example.shopapp.model.accommodation.Accommodation;
import com.example.shopapp.model.review.Review;
import com.example.shopapp.model.user.Guest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;

public class Reservation implements Serializable {

    private long id;
    private double totalPrice;
    private ReservationStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date endDate;
    private int numberOfNights = 1;
    private Accommodation accommodation;
    private Guest guest;
    private List<Review> reviews;

    public Reservation() {
    }

    public Reservation( Long id,int totalPrice,ReservationStatus reservationStatus, Date startDate, Date endDate,int numberOfNights,Accommodation accommodation,Guest guest) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = reservationStatus;
        this.accommodation = accommodation;
        this.guest = guest;
        this.numberOfNights = numberOfNights;
        this.reviews = new ArrayList<>();
    }

    public Reservation( Long id,int totalPrice,ReservationStatus reservationStatus, Date startDate, Date endDate,int numberOfNights,Accommodation accommodation,Guest guest,List<Review> reviews) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = reservationStatus;
        this.accommodation = accommodation;
        this.guest = guest;
        this.numberOfNights = numberOfNights;
        this.reviews = reviews;
    }

    public Reservation( Long id, Date startDate, Date endDate,Accommodation accommodation) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.accommodation = accommodation;
    }
    public Reservation(double totalPrice, ReservationStatus status, Date startDate, Date endDate, int numberOfNights) {
        this.totalPrice = totalPrice;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.numberOfNights = numberOfNights;
    }

    public Reservation(ReservationDTO reservationDTO) {
        this.id = reservationDTO.getId();
        this.totalPrice = reservationDTO.getTotalPrice();
        this.status = reservationDTO.getStatus();
        this.startDate = reservationDTO.getStartDate();
        this.endDate = reservationDTO.getEndDate();
        this.numberOfNights = reservationDTO.getNumberOfNights();
        this.accommodation = reservationDTO.getAccommodation();
        this.guest = reservationDTO.getGuest();
        this.reviews = reservationDTO.getReviews();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getNumberOfNights() {
        return numberOfNights;
    }

    public void setNumberOfNights(int numberOfNights) {
        this.numberOfNights = numberOfNights;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public Accommodation getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", totalPrice=" + totalPrice +
                ", status=" + status +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", numberOfNights=" + numberOfNights +
                ", accommodation=" + accommodation +
                ", guest=" + guest +
                ", reviews=" + reviews +
                '}';
    }

    public void copyValues(Reservation reservation) {
    }
}