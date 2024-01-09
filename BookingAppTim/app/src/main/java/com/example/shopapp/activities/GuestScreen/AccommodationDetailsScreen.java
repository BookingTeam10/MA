package com.example.shopapp.activities.GuestScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.shopapp.R;
import com.example.shopapp.activities.MapsActivity;
import com.example.shopapp.model.accommodation.Accommodation;

public class AccommodationDetailsScreen extends AppCompatActivity {
    private Accommodation accommodation = new Accommodation();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accommodation_details_screen);
        Button myButton = findViewById(R.id.buttonMapsAAA);

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kreiranje Intent-a za pokretanje nove Activity
                Intent intent = new Intent(AccommodationDetailsScreen.this, MapsActivity.class);
                startActivity(intent);
            }
        });
    }
}