package com.example.shopapp.services.interfaces.users;

import com.example.shopapp.dto.OwnerDTO;
import com.example.shopapp.dto.ReservationDTO;
import com.example.shopapp.model.review.Report;
import com.example.shopapp.model.review.ReportAccommodation;
import com.example.shopapp.model.user.Guest;
import com.example.shopapp.model.user.Owner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IOwnerService {
    @GET("owners/requestsSearch")
    Call<ArrayList<ReservationDTO>> searchedRequests(@Query("type") String type,
                                                      @Query("start") String start,
                                                      @Query("end") String  end,
                                                     @Query("nameAccommodation") String  nameAccommodation,
                                                      @Query("IdOwner") Long idOwner
    );

    @GET("owners/{idAccommodation}/reportYear")
    Call<ReportAccommodation> getOwnerReportYear(@Path("idAccommodation") Long idAccommodation);

    @GET("owners/{id}/report")
    Call<ArrayList<Report>> getOwnerReports(@Path("id") Long id,
                                             @Query("start") String start,
                                             @Query("end") String end);

    @GET("owners/username/{username}")
    Call<Owner> getOwnerByUsername(@Path("username") String username);

    @PUT("owners/{id}")
    Call<OwnerDTO> updateOwnerProfile(@Path("id") Long id, @Body Owner updatedOwner);

    @DELETE("owners/{id}")
    Call deleteOwner(@Path("id") Long id);

    @GET("owners/{idOwner}/requestsReservations")
    Call<ArrayList<ReservationDTO>> getOwnersRequests(@Path("idOwner") Long idOwner);

}
