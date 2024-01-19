package com.example.shopapp.fragments.owner.new_accomodation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shopapp.R;

public class OwnerAccommodationFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_owner_accommodation, container, false);


//        // Initialize Button
//        saveProfileButton = view.findViewById(R.id.saveProfileButton);
//        saveProfileButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Handle button click
//            }
//        });

        return view;
    }
}