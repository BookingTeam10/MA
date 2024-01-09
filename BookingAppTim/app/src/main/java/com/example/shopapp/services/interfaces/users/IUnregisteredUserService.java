package com.example.shopapp.services.interfaces.users;

import com.example.shopapp.model.login.LoginDTO;
import com.example.shopapp.model.login.RegistrationDTO;
import com.example.shopapp.model.login.Token;
import com.example.shopapp.model.user.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IUnregisteredUserService {
    @POST("register")
    Call<User> register(@Body RegistrationDTO registrationDTO);

    @POST("activate/{code}")
    Call<String> activateUser(@Path("code") String code);
}
