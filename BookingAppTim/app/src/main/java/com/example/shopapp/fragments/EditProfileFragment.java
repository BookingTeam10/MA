package com.example.shopapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.shopapp.R;
import com.example.shopapp.model.user.User;

public class EditProfileFragment extends Fragment {

    private User currentUser = new User("luka@gmail.com", "aleksa", "Luka", "Popovic", "1232434", "Bul. Pobede 12");
    private EditText emailEditText, nameEditText, surnameEditText, phoneEditText, addressEditText;
    private Button saveProfileButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        // Initialize EditTexts
        emailEditText = view.findViewById(R.id.emailEditText);
        emailEditText.setText(currentUser.getEmail());
        nameEditText = view.findViewById(R.id.nameEditText);
        nameEditText.setText(currentUser.getName());
        surnameEditText = view.findViewById(R.id.surnameEditText);
        surnameEditText.setText(currentUser.getSurname());
        phoneEditText = view.findViewById(R.id.phoneEditText);
        phoneEditText.setText(currentUser.getPhone());
        addressEditText = view.findViewById(R.id.addressEditText);
        addressEditText.setText(currentUser.getAddress());

        // Initialize Button
        saveProfileButton = view.findViewById(R.id.saveProfileButton);
        saveProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button click
            }
        });

        return view;
    }
}