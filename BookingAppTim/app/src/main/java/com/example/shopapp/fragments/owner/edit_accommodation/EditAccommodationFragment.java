package com.example.shopapp.fragments.owner.edit_accommodation;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.example.shopapp.R;
import com.example.shopapp.model.accommodation.Accommodation;
import com.example.shopapp.model.reservation.Reservation;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditAccommodationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditAccommodationFragment extends Fragment {

    Accommodation accommodation;
//    public static EditAccommodationFragment newInstance() {
//        EditAccommodationFragment fragment = new EditAccommodationFragment();
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
//        return fragment;
//    }
    public static EditAccommodationFragment newInstance(Accommodation accommodation) {
        EditAccommodationFragment fragment = new EditAccommodationFragment();
        Bundle args = new Bundle();
        args.putParcelable("accommodation", accommodation);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            accommodation = (Accommodation) getArguments().getParcelable("accommodation");
//            Log.d("USLO2","USLO");
//            Log.d("ACCOMMODATION",accommodation.toString());
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("USLO3","USLO");
        View view = inflater.inflate(R.layout.fragment_edit_accommodation, container, false);
        ScrollView scrollView = view.findViewById(R.id.edit_accommodation_scroll_view);
        // Dalji rad sa scrollView...
        return view;
    }
}