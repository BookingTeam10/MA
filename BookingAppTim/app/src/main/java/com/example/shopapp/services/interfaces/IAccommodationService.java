package com.example.shopapp.services.interfaces;


import com.example.shopapp.model.accommodation.Accommodation;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IAccommodationService {

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("accommodations")
    Call<ArrayList<Accommodation>> getAll();

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("accommodations/{id}")
    Call<Accommodation> getById(@Path("id") Long id);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("accommodationsSearch")
    Call<ArrayList<Accommodation>>  getSearchedAccommodations(
            @Query("location") String location,
            @Query("start") String start, // Datum formatiran kao String
            @Query("end") String end,     // Datum formatiran kao String
            @Query("numPeople") Integer numPeople,
            @Query("minPrice") String minPrice,
            @Query("maxPrice") String maxPrice,
            @Query("amenities") List<String> amenities);


    @GET("owners/accommodations/{idOwner}")
    Call<ArrayList<Accommodation>> getAccommodationByOwner(@Path("idOwner") Long idOwner);
}
