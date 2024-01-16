package com.example.shopapp.services.interfaces;

import com.example.shopapp.dto.ReservationDTO;
import com.example.shopapp.model.reservation.Reservation;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface IReservationService {

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("reservations")
    Call<ArrayList<Reservation>> getGuestsReservations();

    @GET("reservations/byGuest/{idGuest}")
    Call<ArrayList<Reservation>> getReservationsByGuest(@Path("idGuest") Long idGuest);


    @DELETE("request/{reqId}")
    Call<Reservation> deleteGuestReservation(@Path("reqId") Long reqId);
    @GET("reservations/{idAccommodation}/reservations")
    Call<ArrayList<ReservationDTO>> getByAccommodations(@Path("idAccommodation") Long idAccommodation);
}
