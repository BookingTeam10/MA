package com.example.shopapp.fragments.reviews;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shopapp.R;
import com.example.shopapp.model.review.Review;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class ViewReviewFragment extends Fragment {

    Review reviewByReservation;

    public ViewReviewFragment() {

    }

    public static ViewReviewFragment newInstance(String param1, String param2) {
        ViewReviewFragment fragment = new ViewReviewFragment();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            reviewByReservation = (Review) getArguments().getSerializable("reviewByReservation");
            Log.d("UCITA SE LEPO","AAAAAAAA");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_review, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView reviewDetails = view.findViewById(R.id.reviewDetails);

        String describe="";
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = today.format(formatter);
        describe=describe+"Comment: "+reviewByReservation.getComment()+"\n"+ "My rate owner: "+reviewByReservation.getRate()+"\n"+"Date: "+formattedDate+"\n"+"Owner rate: "+5;
        reviewDetails.setText(describe);

    }
}