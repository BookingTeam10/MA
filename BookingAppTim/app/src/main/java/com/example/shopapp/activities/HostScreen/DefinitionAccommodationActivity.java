package com.example.shopapp.activities.HostScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.shopapp.R;
import com.example.shopapp.model.accommodation.Accommodation;

public class DefinitionAccommodationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_definition_accommodation);
        Accommodation accommodation = getIntent().getParcelableExtra("accommodation");
        if (accommodation != null) {
            Log.d("USLOOOOOOOO","ACCCCCCCCCcc");
        }
        Log.d("PROSLEDJENO",accommodation.toString());
    }
}