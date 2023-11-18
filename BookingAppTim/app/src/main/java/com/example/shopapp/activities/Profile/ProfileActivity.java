package com.example.shopapp.activities.Profile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shopapp.R;
import com.example.shopapp.model.User;

import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    private User currentUser = new User("luka@gmail.com", "aleksa", "Luka", "Popovic", "1232434", "Bul. Pobede 12");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        TextView emailTextView = findViewById(R.id.emailTextView);
        TextView nameTextView = findViewById(R.id.nameTextView);
        TextView surnameTextView = findViewById(R.id.surnameTextView);
        TextView phoneTextView = findViewById(R.id.phoneTextView);
        TextView addressTextView = findViewById(R.id.addressTextView);

        // Set the text in TextViews based on the current user
        emailTextView.setText("Email:          " + currentUser.getEmail());
        nameTextView.setText("Name:          " + currentUser.getName());
        surnameTextView.setText("Surname:     " + currentUser.getSurname());
        phoneTextView.setText("Phone:          " + currentUser.getPhone());
        addressTextView.setText("Address:       " + currentUser.getAddress());

        findViewById(R.id.editProfileButton).setOnClickListener(v -> {
            // Replace with EditProfileActivity
            Intent intent = new Intent(this, EditProfileActivity.class);
            startActivity(intent);
        });



    }
}
