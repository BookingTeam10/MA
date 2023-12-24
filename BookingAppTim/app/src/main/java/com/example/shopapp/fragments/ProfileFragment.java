package com.example.shopapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.shopapp.R;
import com.example.shopapp.model.user.User;

public class ProfileFragment extends Fragment {

    private User currentUser = new User("luka@gmail.com", "aleksa", "Luka", "Popovic", "1232434", "Bul. Pobede 12");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        TextView emailTextView = view.findViewById(R.id.emailTextView);
        TextView nameTextView = view.findViewById(R.id.nameTextView);
        TextView surnameTextView = view.findViewById(R.id.surnameTextView);
        TextView phoneTextView = view.findViewById(R.id.phoneTextView);
        TextView addressTextView = view.findViewById(R.id.addressTextView);

        emailTextView.setText("Email:          " + currentUser.getEmail());
        nameTextView.setText("Name:          " + currentUser.getName());
        surnameTextView.setText("Surname:     " + currentUser.getSurname());
        phoneTextView.setText("Phone:          " + currentUser.getPhone());
        addressTextView.setText("Address:       " + currentUser.getAddress());

        Button editProfileButton = view.findViewById(R.id.editProfileButton);

        editProfileButton.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.nav_edit_profile);
        });


        return view;
    }
}