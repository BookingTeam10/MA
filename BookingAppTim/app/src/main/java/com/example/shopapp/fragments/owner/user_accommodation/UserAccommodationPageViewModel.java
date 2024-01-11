package com.example.shopapp.fragments.owner.user_accommodation;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shopapp.R;
import com.example.shopapp.configuration.ServiceUtils;
import com.example.shopapp.model.accommodation.Accommodation;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserAccommodationPageViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<Accommodation>> accommodations = new MutableLiveData<>();
    public UserAccommodationPageViewModel(){
    }

    public LiveData<ArrayList<Accommodation>> getAccommodations() {
        return accommodations;
    }

    public void setAccommodations(ArrayList<Accommodation> accommodations) {
        this.accommodations.setValue(accommodations);
        Log.d("PROMENI SE", String.valueOf(accommodations.size()));
    }

    void getDataByOwner(){  //o o izmeniti
        //Call<ArrayList<Accommodation>> call = ServiceUtils.accommodationService.getAll();

        Call<ArrayList<Accommodation>> call = ServiceUtils.accommodationService.getAccommodationByOwner(1L);
        call.enqueue(new Callback<ArrayList<Accommodation>>() {
            @Override
            public void onResponse(Call<ArrayList<Accommodation>> call, Response<ArrayList<Accommodation>> response) {
                if (response.code() == 200){
                    System.out.println(response.body());
                    ArrayList<Accommodation> accommodationsList = response.body();
                    accommodations.setValue(accommodationsList);
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Accommodation>> call, Throwable t) {
                Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
            }
        });
    }
}