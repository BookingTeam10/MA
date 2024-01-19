package com.example.shopapp.services.interfaces.users;

import com.example.shopapp.model.review.ReviewOwner;
import com.example.shopapp.model.user.ReportUser;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IReportUserService {

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })

    @GET("reportUser/GO/{idOwner}/{idGuest}")
    Call<ReportUser> getUserReportGO(@Path("idOwner") Long idOwner,@Path("idGuest") Long idGuest);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })

    @GET("reportUser/OG/{idOwner}/{idGuest}")
    Call<ReportUser> getUserReportOG(@Path("idOwner") Long idOwner,@Path("idGuest") Long idGuest);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })

    @POST("reportUser/{idOwner}/{idGuest}")
    Call<ReportUser> addReport(@Path("idOwner") Long idOwner,@Path("idGuest") Long idGuest,@Body ReportUser reportUser);

}
