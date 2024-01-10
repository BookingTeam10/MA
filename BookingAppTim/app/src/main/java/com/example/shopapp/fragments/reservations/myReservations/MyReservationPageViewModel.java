package com.example.shopapp.fragments.reservations.myReservations;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.shopapp.model.accommodation.Accommodation;
import com.example.shopapp.model.reservation.Reservation;

import java.util.ArrayList;

public class MyReservationPageViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<Reservation>> reservations = new MutableLiveData<>();

    public MyReservationPageViewModel(){
    }

    public LiveData<ArrayList<Reservation>> getReservations() {
        return reservations;
    }

    public void setReservations(ArrayList<Reservation> reservations) {
        this.reservations.setValue(reservations);
    }
}
