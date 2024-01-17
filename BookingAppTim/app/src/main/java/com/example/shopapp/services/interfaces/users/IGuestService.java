package com.example.shopapp.services.interfaces.users;

import com.example.shopapp.dto.AccommodationDTO;
import com.example.shopapp.dto.GuestDTO;
import com.example.shopapp.dto.ReservationDTO;
import com.example.shopapp.model.accommodation.Accommodation;
import com.example.shopapp.model.reservation.Reservation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IGuestService {
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
//    @POST("guests/reservations")
//    Call<ReservationDTO> createReservation(@Body Reservation reservation);
    @POST("guests/reservationsMobile")
    Call<ReservationDTO> createReservation(@Query("price") double price,
                                            @Query("start") String start, // Datum formatiran kao String
                                           @Query("end") String end,
                                           @Query("idAccommodation") Long idAccommodation,
                                           @Query("idGuest") Long idGuest,
                                            @Query("automatic") boolean automatic);
            ;

    //@POST("guests/{id}/favouriteAccommodations/add")
    //Call<GuestDTO> addFavouriteAccommodation(@Path("id") Long id, @Body Accommodation accommodation);

    @DELETE("guests/{idGuest}/favouriteAccommodations/{idAccommodation}")
    Call<GuestDTO> deleteFavouriteAccommodation(@Path("idGuest") Long idGuest,@Path("idAccommodation") Long idAccommodation);

    @GET("guests/{idGuest}/requests")
    Call<ArrayList<ReservationDTO>> allGuestRequests(@Path("idGuest") Long idGuest);

    @DELETE("guests/request/{reqId}")
    Call<ReservationDTO> deleteGuestReservation(@Path("reqId") Long reqId);

    @GET("guests/{id}/favouriteAccommodations")
    Call<ArrayList<AccommodationDTO>> getFavouriteAccommodation(@Path("id") Long id);

    @GET(value = "guests/{idGuest}/not-accepted-requests")
    Call<ArrayList<ReservationDTO>> notAcceptedReservationByGuest(@Path("idGuest") Long idGuest);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @POST("guests/{id}/favouriteAccommodationsMobile/add/{idAccommodation}")
    Call<GuestDTO> addFavouriteAccommodation(@Path("id") Long id,@Path("idAccommodation") Long idAccommodation);
}
