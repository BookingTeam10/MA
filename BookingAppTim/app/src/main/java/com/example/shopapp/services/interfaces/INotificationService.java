package com.example.shopapp.services.interfaces;

import com.example.shopapp.model.notifications.NotificationUserVisible;
import com.example.shopapp.model.user.Owner;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface INotificationService {
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("notifications/mobileGuest/{idGuest}")
    Call<ArrayList<NotificationUserVisible>> getAllGuest(@Path("idGuest") Long idGuest);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("notifications/mobileOwner/{idOwner}")
    Call<ArrayList<NotificationUserVisible>> getAllOwner(@Path("idOwner") Long idOwner);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @POST("notifications/createNotification")
    Call<NotificationUserVisible> createNotification(@Query("text") String text,
                                                     @Query("idGuest")Long idGuest,
                                                     @Query("idOwner")Long idOwner,
                                                     @Query("userRate")String userRate);
}
