package com.example.shopapp.fragments.guest.reservations;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shopapp.R;
import com.example.shopapp.adapters.ReservationListAdapter;
import com.example.shopapp.configuration.ServiceUtils;
import com.example.shopapp.dto.ReservationDTO;
import com.example.shopapp.model.accommodation.Accommodation;
import com.example.shopapp.model.reservation.Reservation;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestFragment extends Fragment {
    private RecyclerView recyclerView;
    private ReservationListAdapter adapter;
    private List<Reservation> reservationList;
    private List<ReservationDTO> reservationDTOS;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_request, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        getGuestRequests();


        return view;
    }

    private void getGuestRequests(){
        reservationList = new ArrayList<>();
        Call<ArrayList<ReservationDTO>> call = ServiceUtils.guestService.allGuestRequests(3L);
        Log.d("CALL", call.toString());
        call.enqueue(new Callback<ArrayList<ReservationDTO>>() {
            @Override
            public void onResponse(Call<ArrayList<ReservationDTO>> call, Response<ArrayList<ReservationDTO>> response) {
                if (response.code() == 200){
                    reservationDTOS = response.body();
                    for(ReservationDTO reservationDTO : reservationDTOS){
                        reservationList.add(new Reservation(reservationDTO));
                    }
                    ReservationListAdapter adapter = new ReservationListAdapter(reservationList);
                    recyclerView.setAdapter(adapter);

                }
            }

            @Override
            public void onFailure(Call<ArrayList<ReservationDTO>> call, Throwable t) {
                Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
            }
        });

    }
}