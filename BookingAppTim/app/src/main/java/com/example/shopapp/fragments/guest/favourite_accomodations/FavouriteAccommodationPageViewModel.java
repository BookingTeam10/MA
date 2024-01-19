package com.example.shopapp.fragments.guest.favourite_accomodations;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.shopapp.configuration.ServiceUtils;
import com.example.shopapp.dto.AccommodationDTO;
import com.example.shopapp.dto.GuestDTO;
import com.example.shopapp.model.accommodation.Accommodation;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavouriteAccommodationPageViewModel extends ViewModel {
    private final MutableLiveData<String> searchText;
    private final MutableLiveData<ArrayList<Accommodation>> accommodations = new MutableLiveData<>();
    public FavouriteAccommodationPageViewModel(){
        searchText = new MutableLiveData<>();
        searchText.setValue("This is search help!");
    }
    public LiveData<String> getText(){
        return searchText;
    }
    public LiveData<ArrayList<Accommodation>> getAccommodations() {
        return accommodations;
    }

    public void setAccommodations(ArrayList<Accommodation> accommodations) {
        this.accommodations.setValue(accommodations);
    }
    public void getFavouriteAccommodations(Long id){
        Call<ArrayList<AccommodationDTO>> call = ServiceUtils.guestService.getFavouriteAccommodation(3L);
        call.enqueue(new Callback<ArrayList<AccommodationDTO>>() {
            @Override
            public void onResponse(Call<ArrayList<AccommodationDTO>> call, Response<ArrayList<AccommodationDTO>> response) {
                if (response.code() == 200){
                    ArrayList<AccommodationDTO> accommodationDTOS = response.body();
                    ArrayList<Accommodation> accommodationsList = new ArrayList<>();

                    for(AccommodationDTO accommodationDTO:accommodationDTOS){
                        accommodationsList.add(new Accommodation(accommodationDTO));
                    }
                    accommodations.setValue(accommodationsList);
                }
            }
            @Override
            public void onFailure(Call<ArrayList<AccommodationDTO>> call, Throwable t) {
                Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
            }
        });
    }

}
