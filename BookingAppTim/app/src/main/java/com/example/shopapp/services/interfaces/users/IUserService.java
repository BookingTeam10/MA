package com.example.shopapp.services.interfaces.users;

import com.example.shopapp.dto.PasswordDto;
import com.example.shopapp.model.login.LoginDTO;
import com.example.shopapp.model.login.Token;
import com.example.shopapp.model.review.UserReport;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IUserService {
    @POST("users/login")
    Call<Token> loginUser(@Body LoginDTO login);

    @PUT("users/change-password/{id}")
    Call<Void> changePassword(@Path("id") Long id, @Body PasswordDto passwordDto);

    @PUT("users/block/{id}")
    Call<Void> blockUser(@Path("id") Long id, @Body
    UserReport report);

    @GET("users/is-blocked/{username}")
    Call<Boolean> isBlocked(@Path("username") String username);
}
