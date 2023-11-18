package com.example.shopapp.activities.Profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.shopapp.R;
import com.example.shopapp.model.User;

public class EditProfileActivity extends AppCompatActivity {
    private User currentUser = new User("luka@gmail.com", "aleksa", "Luka", "Popovic", "1232434", "Bul. Pobede 12");
    private EditText emailEditText, nameEditText, surnameEditText, phoneEditText, addressEditText;
    private Button saveProfileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // Initialize EditTexts
        emailEditText = findViewById(R.id.emailEditText);
        emailEditText.setText(currentUser.getEmail());
        nameEditText = findViewById(R.id.nameEditText);
        nameEditText.setText(currentUser.getName());
        surnameEditText = findViewById(R.id.surnameEditText);
        surnameEditText.setText(currentUser.getSurname());
        phoneEditText = findViewById(R.id.phoneEditText);
        phoneEditText.setText(currentUser.getPhone());
        addressEditText = findViewById(R.id.addressEditText);
        addressEditText.setText(currentUser.getAddress());

        // Initialize Button
        saveProfileButton = findViewById(R.id.saveProfileButton);
        saveProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button click
            }
        });
    }
}

