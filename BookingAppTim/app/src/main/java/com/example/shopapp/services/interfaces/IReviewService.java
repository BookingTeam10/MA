package com.example.shopapp.services.interfaces;

import com.example.shopapp.dto.ReviewDTO;
import com.example.shopapp.model.review.Review;
import com.example.shopapp.model.review.ReviewOwner;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IReviewService {

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("reservations/{id}")
    Call<Review> getReviewByReservation(Long id);

    @GET("reviews/rateFull/{idOwner}/{idGuest}")
    Call<ReviewOwner> getReviewOwnerByReservation(@Path("idOwner") Long idOwner,@Path("idGuest") Long idGuest);
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @POST("reviews/rate/{idOwner}/{idGuest}")
    Call<ReviewOwner> addRate(@Path("idOwner") Long idOwner,@Path("idGuest") Long idGuest,@Body ReviewOwner reviewOwner);

    @DELETE("reviews/rateDelete/{idOwner}/{idGuest}")
    Call<ResponseBody> deleteById(@Path("idOwner") Long idOwner,@Path("idGuest") Long idGuest);

    @GET("reviews/byReservationId/{id}")
    Call<Review> getReviewByReservationId(@Path("id") Long id);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @POST("reviews")
    Call<ReviewDTO> createReview(@Body Review review);

    @DELETE("reviews/{id}")
    Call<ResponseBody> deleteByIdReview(@Path("id") Long id);

    @GET("reviews/byReservationIdSingle/{id}")
    Call<Review> getReviewByReservationIdSingle(@Path("id") Long id);


    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @POST("reviews/mobile")
    Call<ReviewDTO> createReviewAllParam(@Query("rate") double rate,
                                         @Query("text") String text,
                                         @Query("reservationId") Long reservationId
                                        );

    @GET("reviews/reportCommentAccommodationByOwner/{id}")
    Call<Review> getReportComment(@Path("id") Long id);

    @GET("reviews")
    Call<ArrayList<Review>> getAllAccReviews();


    @PUT("reviews/approve/{id}")
    Call<Void> approveReview(@Path("id") Long id, @Body Review review);

    @DELETE("reviews/{id}")
    Call<Void> deleteReview(@Path("id") Long id);

    @GET("reviews/reviewOwners")
    Call<ArrayList<ReviewOwner>> getAllOwnerReviews();

    @PUT("reviews/reviewOwners/approve/{id}?")
    Call<Void> updateReviewOwner(@Path("id") Long id, @Body ReviewOwner reviewOwner, @Query("approve") boolean approve);
}
