package com.example.shopapp.activities.GuestScreen;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.shopapp.configuration.ServiceUtils;
import com.example.shopapp.dto.ReservationDTO;
import com.example.shopapp.model.accommodation.Accommodation;
import com.example.shopapp.model.review.Review;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccommodationDetailsViewModel extends ViewModel {
    private MutableLiveData<ArrayList<ReservationDTO>> reservationDTOS = new MutableLiveData<>();
    private List<Review> reviews = new ArrayList<>();
    private MutableLiveData<List<Review>> reviewsLiveData = new MutableLiveData<>();

    public LiveData<ArrayList<ReservationDTO>> getReservations() {
        return reservationDTOS;
    }

    public LiveData<List<Review>> getReviewsLiveData() {
        return reviewsLiveData;
    }

     void loadReservations(Accommodation accommodation){
        Call<ArrayList<ReservationDTO>> call = ServiceUtils.reservationService.getByAccommodations(accommodation.getId());
        call.enqueue(new Callback<ArrayList<ReservationDTO>>() {
            @Override
            public void onResponse(Call<ArrayList<ReservationDTO>> call, Response<ArrayList<ReservationDTO>> response) {
                if (response.code() == 200){
                    System.out.println(response.body());
                    reservationDTOS.postValue(response.body());
                    //loadReviews();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<ReservationDTO>> call, Throwable t) {
                Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
            }
        });
    }

    void loadReviews(List<ReservationDTO> reservationDTOS){
        for(ReservationDTO reservationDTO:reservationDTOS){

            Call<Review> call = ServiceUtils.reviewService.getReviewByReservationId(reservationDTO.getId());
            call.enqueue(new Callback<Review>() {
                @Override
                public void onResponse(Call<Review> call, Response<Review> response) {
                    Log.i("NE UDJE U 200",response.body().toString());
                    if (response.code() == 200){
                        Log.i("UDJE U 200",response.body().toString());
                        System.out.println(response.body());
                        reviews.add(response.body());

                    }
                }

                @Override
                public void onFailure(Call<Review> call, Throwable t) {
                    Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
                }
            });
        }
    }
}
