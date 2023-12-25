package com.example.shopapp.model.reservation;
import com.example.shopapp.model.accommodation.Accommodation;

import java.time.LocalDate;
import java.util.Date;
public class Reservation {
    private long id;
    private Date startDate;
    private Date endDate;
    private Accommodation accommodation;
    public Reservation() {

    }
    public Reservation(long id) {
        this.id = id;
    }

    public Reservation(long id,Date startDate, Date endDate,Accommodation accommodation) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
       this.accommodation = accommodation;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Accommodation getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
    }
}

