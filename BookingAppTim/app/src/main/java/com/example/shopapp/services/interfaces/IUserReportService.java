package com.example.shopapp.services.interfaces;

import com.example.shopapp.model.review.UserReport;
import com.example.shopapp.model.user.Owner;
import com.example.shopapp.model.user.ReportUser;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface IUserReportService {
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })

    @GET("reportUser")
    Call<ArrayList<UserReport>> getAll();

    @DELETE("reportUser/{id}")
    Call<Void> delete(@Path("id") int id);

}
