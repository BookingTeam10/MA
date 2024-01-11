package com.example.shopapp.fragments.owner.requests;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.shopapp.R;
import com.example.shopapp.adapters.ReservationListAdapter;
import com.example.shopapp.configuration.ServiceUtils;
import com.example.shopapp.dto.ReservationDTO;
import com.example.shopapp.model.reservation.Reservation;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListRequestFragment extends Fragment {

    private RecyclerView recyclerView;
    private ReservationListAdapter adapter;
    private List<Reservation> reservationList;
    private List<ReservationDTO> reservationDTOS;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_request, container, false);

        Button buttonSearch = view.findViewById(R.id.buttonSearch);
        Spinner spinnerRequestStatus =  view.findViewById(R.id.spinnerRequestStatus);
        ArrayAdapter<String> arrayAdapterStatus = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.request_status));
        arrayAdapterStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRequestStatus.setAdapter(arrayAdapterStatus);
        EditText nameAccommodationEditText = view.findViewById(R.id.nameAccommodation);
        String nameAccommodation = nameAccommodationEditText.getText().toString();

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //ovo prepraviti u nas slucaj
       getGuestRequests();
        buttonSearch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.i("UDjE OVDE","UDJE OVDE");
               searchRequests(nameAccommodation);
            }
        });

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
    private void searchRequests(String nameAccommodation) {
        Log.i("CITANJE IZ TEXTBOXA",nameAccommodation);
    }
}