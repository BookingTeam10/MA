package com.example.shopapp.services.interfaces.users;

import com.example.shopapp.dto.ReservationDTO;
import com.example.shopapp.model.review.Report;
import com.example.shopapp.model.review.ReportAccommodation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import retrofit2.Call;
import retrofit2.http.GET;
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

    @GET("owners/{idOwner}/requestsReservations")
    Call<ArrayList<ReservationDTO>> getOwnersRequests(@Path("idOwner") Long idOwner);

}
