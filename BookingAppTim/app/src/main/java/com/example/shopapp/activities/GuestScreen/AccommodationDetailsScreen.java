package com.example.shopapp.activities.GuestScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.shopapp.R;
import com.example.shopapp.activities.MapsActivity;
import com.example.shopapp.model.accommodation.Accommodation;

import androidx.appcompat.app.AppCompatActivity;

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
import com.example.shopapp.model.reservation.Reservation;

import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccommodationDetailsScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accommodation_details_screen);
        //Accommodation accommodation = (Accommodation) getIntent().getSerializableExtra("accommodation");
        Accommodation accommodation = getIntent().getParcelableExtra("accommodation");
        //Log.d("AMENITY",accommodation.getAmenities().toString());
        TextView tvHotelName = findViewById(R.id.tvHotelName);
        //ImageView ivHotel = findViewById(R.id.ivHotel);
        TextView tvHotelDescription = findViewById(R.id.tvHotelDescription);
        TextView textViewAmenity = findViewById(R.id.textViewAmenity);
        TextView textViewPrice = findViewById(R.id.textViewPrice);

        tvHotelName.setText(accommodation.getName());

        tvHotelDescription.setText(accommodation.getDescription());
        //textViewAmenity.setText("Amenity: " + accommodation.getAmenities()); // Pretpostavka da postoji getAmenity metoda
        //textViewPrice.setText("Cena za 1 noćenje je " + accommodation.getPrices()); // Pretpostavka da postoji getPrice metoda

        Button mapButton = findViewById(R.id.buttonMapsAAA);
        Button buttonBook = findViewById(R.id.buttonBook);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Logika za pokretanje MapsActivity
                Intent intent = new Intent(AccommodationDetailsScreen.this, MapsActivity.class);
                startActivity(intent);
            }
        });

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
                Reservation reservation = new Reservation(100L,100,ReservationStatus.WAITING,null,null,1,accommodation,null);

                Toast.makeText(AccommodationDetailsScreen.this, "Book now clicked!", Toast.LENGTH_SHORT).show();
                Call<ReservationDTO> call = ServiceUtils.guestService.createReservation(reservation);

                call.enqueue(new Callback<ReservationDTO>() {
                    @Override
                    public void onResponse(Call<ReservationDTO> call, Response<ReservationDTO> response) {
                        if(!response.isSuccessful()){
                            Log.d("FAIL", "FAIL");
                            return;
                        }
                        Log.d("FINISH", "Successfully finished ");
                    }

                    @Override
                    public void onFailure(Call<ReservationDTO> call, Throwable t) {
                        Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
                    }
                });
            }
        });
    }
}