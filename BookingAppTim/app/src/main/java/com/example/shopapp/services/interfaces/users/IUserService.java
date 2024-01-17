package com.example.shopapp.services.interfaces.users;

import com.example.shopapp.model.login.LoginDTO;
import com.example.shopapp.model.login.Token;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IUserService {
    @POST("users/login")
    Call<Token> loginUser(@Body LoginDTO login);
}
