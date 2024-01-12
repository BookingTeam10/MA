package com.example.shopapp.model.review;


import com.example.shopapp.model.accommodation.Accommodation;

public class Report {
    Accommodation accommodation;
    int numberReservation;
    int profit;

    public Accommodation getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
    }

    public int getNumberReservation() {
        return numberReservation;
    }

    public void setNumberReservation(int numberReservation) {
        this.numberReservation = numberReservation;
    }

    public int getProfit() {
        return profit;
    }

    public void setProfit(int profit) {
        this.profit = profit;
    }

    @Override
    public String toString() {
        return "Report{" +
                "accommodation=" + accommodation +
                ", numberReservation=" + numberReservation +
                ", profit=" + profit +
                '}';
    }

    public Report(Accommodation accommodation, int numberReservation, int profit) {
        this.accommodation = accommodation;
        this.numberReservation = numberReservation;
        this.profit = profit;
    }
}


