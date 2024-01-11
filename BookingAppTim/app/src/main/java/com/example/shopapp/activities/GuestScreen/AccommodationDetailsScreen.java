package com.example.shopapp.activities.GuestScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.shopapp.R;
import com.example.shopapp.activities.MapsActivity;
import com.example.shopapp.adapters.MyReservationListAdapter;
import com.example.shopapp.model.accommodation.Accommodation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopapp.R;
import com.example.shopapp.activities.MapsActivity;
import com.example.shopapp.configuration.ServiceUtils;
import com.example.shopapp.dto.ReservationDTO;
import com.example.shopapp.enums.ReservationStatus;
import com.example.shopapp.model.accommodation.Accommodation;
import com.example.shopapp.model.accommodation.Amenity;
import com.example.shopapp.model.reservation.Reservation;
import com.example.shopapp.model.review.Review;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccommodationDetailsScreen extends AppCompatActivity {

    private AccommodationDetailsViewModel viewModel;
    ArrayList<ReservationDTO> reservationDTOS = new ArrayList<>();
    ArrayList<Review> reviews = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accommodation_details_screen);
        Accommodation accommodation = getIntent().getParcelableExtra("accommodation");
        TextView tvHotelName = findViewById(R.id.tvHotelName);
        //ImageView ivHotel = findViewById(R.id.ivHotel);
        TextView tvHotelDescription = findViewById(R.id.tvHotelDescription);
        TextView textViewAmenity = findViewById(R.id.textViewAmenity);
        TextView textViewPrice = findViewById(R.id.textViewPrice);
        tvHotelName.setText(accommodation.getName());

        tvHotelDescription.setText(accommodation.getDescription());
        StringBuilder amenitiesBuilder = new StringBuilder();
        for (Amenity amenity : accommodation.getAmenities()) {
            amenitiesBuilder.append(amenity.getName()).append(", ");
        }
        if (amenitiesBuilder.length() > 0) {
            amenitiesBuilder.setLength(amenitiesBuilder.length() - 2);
        }
        textViewAmenity.setText("Amenity: " + amenitiesBuilder.toString());

        double price = 0;
        if (accommodation.getPrices() != null && !accommodation.getPrices().isEmpty()) {
            price = accommodation.getPrices().get(0).getPrice(); // Pretpostavka da postoji getPrice metoda
        }
        textViewPrice.setText("Cena za 1 noćenje je " + price);
        //loadReservations(accommodation);


        Button mapButton = findViewById(R.id.buttonMapsAAA);
        Button buttonBook = findViewById(R.id.buttonBook);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccommodationDetailsScreen.this, MapsActivity.class);
                startActivity(intent);
            }
        });

        SharedPreferences sharedPreferences = this.getSharedPreferences("preferences", Context.MODE_PRIVATE);
        String role = sharedPreferences.getString("pref_role", "undefined");

        if (!role.equals("Guest")) {
            buttonBook.setVisibility(View.INVISIBLE);
        }
        buttonBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar startCal = Calendar.getInstance();
                startCal.set(2025, Calendar.JANUARY, 1);
                Date startDate = startCal.getTime();

                // Postavljanje kalendara na krajnji datum
                Calendar endCal = Calendar.getInstance();
                endCal.set(2025, Calendar.JANUARY, 15);
                Date endDate = endCal.getTime();
                Reservation reservation = new Reservation(100L, 100, ReservationStatus.WAITING, null, null, 1, accommodation, null);

                Toast.makeText(AccommodationDetailsScreen.this, "Book now clicked!", Toast.LENGTH_SHORT).show();
                Call<ReservationDTO> call = ServiceUtils.guestService.createReservation(reservation);

                call.enqueue(new Callback<ReservationDTO>() {
                    @Override
                    public void onResponse(Call<ReservationDTO> call, Response<ReservationDTO> response) {
                        if (!response.isSuccessful()) {
                            Log.d("FAIL", "FAIL");
                            return;
                        }
                        Log.d("FINISH", "Successfully finished ");
                    }

                    @Override
                    public void onFailure(Call<ReservationDTO> call, Throwable t) {
                        Log.d("REZ", t.getMessage() != null ? t.getMessage() : "error");
                    }
                });
            }
        });
//        viewModel = new ViewModelProvider(this).get(AccommodationDetailsViewModel.class);
//
//        viewModel.getReservations().observe(this, reservationDTOS -> {
//            // Ažurirajte globalnu promenljivu i UI
//            reservations = new ArrayList<>(reservationDTOS);
//            // Ovde ažurirajte UI sa listom rezervacija
//            Log.d("RESERVATIONS UCITANO", reservations.toString());
//            // Nakon što su rezervacije učitane, pokrenite učitavanje recenzija
//            viewModel.loadReviews(reservations);
//        });
//
//// Posmatrajte LiveData za recenzije
//        viewModel.getReviewsLiveData().observe(this, reviewsLoad -> {
//            // Ažurirajte globalnu promenljivu i UI
//            reviews = new ArrayList<>(reviewsLoad);
//            // Ovde ažurirajte UI sa listom recenzija
//            Log.d("REVIEWS UCITANO", reviews.toString());
    }

    private void loadReservations(Accommodation accommodation){
        Call<ArrayList<ReservationDTO>> call = ServiceUtils.reservationService.getByAccommodations(accommodation.getId());
        call.enqueue(new Callback<ArrayList<ReservationDTO>>() {
            @Override
            public void onResponse(Call<ArrayList<ReservationDTO>> call, Response<ArrayList<ReservationDTO>> response) {
                if (response.code() == 200){
                    System.out.println(response.body());
                    reservationDTOS = response.body();
                    Log.i("NAPUNI ", String.valueOf(reservationDTOS.size()));
                    loadReviews();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<ReservationDTO>> call, Throwable t) {
                Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
            }
        });
    }

    private void loadReviews(){
        Log.i("NE UDJE U 200", String.valueOf(reservationDTOS.size()));
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
