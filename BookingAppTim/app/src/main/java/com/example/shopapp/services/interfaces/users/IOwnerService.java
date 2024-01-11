package com.example.shopapp.services.interfaces.users;

import com.example.shopapp.dto.ReservationDTO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IOwnerService {
    @GET("owners/requestsSearch")
    Call<ArrayList<ReservationDTO>> searchedRequests(@Query("type") String type,
                                                      @Query("start") String start,
                                                      @Query("end") String  end,
                                                     @Query("nameAccommodation") String  nameAccommodation,
                                                      @Query("IdOwner") Long idOwner
    );
}
