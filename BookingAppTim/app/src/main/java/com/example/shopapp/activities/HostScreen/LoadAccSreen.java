package com.example.shopapp.activities.HostScreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shopapp.R;
import com.example.shopapp.fragments.new_accomodation.EditAccommodationActivity;

public class LoadAccSreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_load_accommodation);

        Button btnEdit= findViewById(R.id.edit_definition_button);
        Button btnHome= findViewById(R.id.btnHome);

        btnEdit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoadAccSreen.this, EditAccommodationActivity.class);
                startActivity(intent);
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoadAccSreen.this, HostMainActivity.class);
                startActivity(intent);
            }
        });

    }
}
