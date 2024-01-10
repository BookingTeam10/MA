package com.example.shopapp.fragments.guest;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shopapp.R;
import com.example.shopapp.adapters.ReservationListAdapter;
import com.example.shopapp.model.accommodation.Accommodation;
import com.example.shopapp.model.reservation.Reservation;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RequestFragment extends Fragment {
    private RecyclerView recyclerView;
    private ReservationListAdapter adapter;
    private List<Reservation> reservationList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_request, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date date1;
        Date date2;
        try {
            date1 = format.parse("01-01-2024");
            date2 = format.parse("05-01-2024");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Accommodation accommodation = new Accommodation(1L, "Aparment in Novi Sad, Gunduliceva 21a", "This aparment in Novi Sad.", R.drawable.apartment1);
        List<Reservation> reservationList = new ArrayList<>();
        reservationList.add(new Reservation(1,date1,date1,accommodation));
        reservationList.add(new Reservation(2,date2,date2,accommodation));
        reservationList.add(new Reservation(2,date2,date2,accommodation));
        // Postavljanje adaptera
        ReservationListAdapter adapter = new ReservationListAdapter(reservationList);
        recyclerView.setAdapter(adapter);

        return view;
    }
}