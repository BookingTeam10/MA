package com.example.shopapp.services.interfaces;


import com.example.shopapp.model.accommodation.Accommodation;
import com.example.shopapp.model.accommodation.Price;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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
    @GET("accommodations/accommodationsSearch")
    Call<ArrayList<Accommodation>>  getSearchedAccommodations(
            @Query("location") String location,
            @Query("start") String start,
            @Query("end") String end,
            @Query("numPeople") Integer numPeople,
            @Query("minPrice") String minPrice,
            @Query("maxPrice") String maxPrice,
            @Query("amenities") List<String> amenities);


    @GET("owners/accommodations/{idOwner}")
    Call<ArrayList<Accommodation>> getAccommodationByOwner(@Path("idOwner") Long idOwner);

    @POST("accommodations/add")
    Call<Accommodation> createAccommodation(@Body Accommodation accommodation);

//    @PUT("accommodations/{id}")
//    Call<Accommodation> updateAccommodation(@Body Accommodation accommodation,@Path("id") Long id);
  
    @Headers({
        "User-Agent: Mobile-Android",
        "Content-Type:application/json"
    })
    @PUT("accommodations/{id}")
    Call<Map<String, String>> updateAccommodation(@Body Accommodation accommodation, @Path("id") Long id);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @PUT("accommodations/mobile")
    Call<Map<String, String>> updateAccommodationMobile(
            @Query("idAccommodation") Long idAccommodation,
            @Query("weekendPrice")  double weekendPrice,
            @Query("holidayPrice")  double holidayPrice,
            @Query("summerPrice") double summerPrice,
            @Query("isNight") boolean isNight,
            @Query("cancelDeadline") int cancelDeadline
    );

    @POST("accommodations/approve/{id}")
    Call<Void> approveAccommodation(@Path("id") Long id , @Body Accommodation accommodation);
    @POST("accommodations/reject/{id}")
    Call<Void> rejectAccommodation(@Path("id") Long id, @Body Accommodation accommodation);

}
