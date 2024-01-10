package com.example.shopapp.fragments.accomodations;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.shopapp.model.accommodation.Accommodation;

import java.util.ArrayList;

public class AccomodationPageViewModel extends ViewModel {
    private final MutableLiveData<String> searchText;
    private final MutableLiveData<ArrayList<Accommodation>> accommodations = new MutableLiveData<>();
    public AccomodationPageViewModel(){
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
}
