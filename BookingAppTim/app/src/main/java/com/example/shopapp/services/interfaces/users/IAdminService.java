package com.example.shopapp.services.interfaces.users;

import com.example.shopapp.dto.AdministratorDTO;
import com.example.shopapp.dto.GuestDTO;
import com.example.shopapp.model.user.Administrator;
import com.example.shopapp.model.user.Guest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
public interface IAdminService {
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type: application/json"
    })

    @GET("admin/username/{username}")
    Call<Administrator> getAdministratorByUsername(@Path("username") String username);

    @DELETE("admin/{id}")
    Call<Void> deleteAdmin(@Path("id") Long id);

    @PUT("admin/{id}")
    Call<AdministratorDTO> updateAdminProfile(@Path("id") Long id, @Body Administrator updatedAdmin);
}
