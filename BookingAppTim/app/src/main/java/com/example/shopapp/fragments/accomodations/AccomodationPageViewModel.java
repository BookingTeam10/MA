package com.example.shopapp.fragments.accomodations;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.shopapp.configuration.ServiceUtils;
import com.example.shopapp.dto.AccommodationDTO;
import com.example.shopapp.dto.GuestDTO;
import com.example.shopapp.model.accommodation.Accommodation;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccomodationPageViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<Accommodation>> accommodations = new MutableLiveData<>();
    public AccomodationPageViewModel(){

    }
    public LiveData<ArrayList<Accommodation>> getAccommodations() {
        return accommodations;
    }

    public void setAccommodations(ArrayList<Accommodation> accommodations) {
        this.accommodations.postValue(accommodations);
    }
    void getDataFromClient(){
        Call<ArrayList<Accommodation>> call = ServiceUtils.accommodationService.getAll();
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
