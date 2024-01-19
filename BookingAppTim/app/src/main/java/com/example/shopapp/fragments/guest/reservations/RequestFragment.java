package com.example.shopapp.fragments.guest.reservations;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;

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
    private RecyclerView recyclerViewGuest;
    private ReservationListAdapter adapter;
    private List<Reservation> reservationList;
    private List<Reservation> reservationList2;
    private List<ReservationDTO> reservationDTOS;
    private List<ReservationDTO> reservationDTOS2;
    String date1 = "";
    String date2 = "";
    private CalendarView calendarView1;
    private CalendarView calendarView2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_request, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);


        Button buttonSearch = view.findViewById(R.id.buttonSearchGuest);
        Spinner spinnerRequestStatus =  view.findViewById(R.id.spinnerRequestStatusGuest);
        ArrayAdapter<String> arrayAdapterStatus = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.request_status));
        arrayAdapterStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRequestStatus.setAdapter(arrayAdapterStatus);
        EditText nameAccommodationEditText = view.findViewById(R.id.nameAccommodationGuest);
        String nameAccommodation = nameAccommodationEditText.getText().toString();

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerViewGuest = view.findViewById(R.id.recyclerViewAllRequestsGuest);
        recyclerViewGuest.setLayoutManager(new LinearLayoutManager(getActivity()));

      // calendarView1 = view.findViewById(R.id.calendarViewForSearch1);
      // calendarView2 = view.findViewById(R.id.calendarViewForSearch2);



//        calendarView1.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
//            @Override
//            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
//                // Implement your logic for calendarView1 date change here
//                // The month value is 0-based (i.e., 0 for January)
//                Log.i("UDJE OVDE1","UDJE OVDE1");
//                date1 = handleDateChange(calendarView1, year, month, dayOfMonth);
//                Log.i("UDJE OVDE2",date1);
//            }
//        });
//
//        calendarView2.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
//            @Override
//            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
//                // Implement your logic for calendarView2 date change here
//                date2 = handleDateChange(calendarView2, year, month, dayOfMonth);
//                Log.i("UDJE OVDE3",date2);
//            }
//        });
        getGuestRequests();
        getDeleteRequests();

        buttonSearch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.i("UDjE OVDE","UDJE OVDE");
                String selectedStatus = spinnerRequestStatus.getSelectedItem().toString();
                searchRequests(nameAccommodation,selectedStatus);
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
                    //ReservationListAdapter adapter1 = new ReservationListAdapter(reservationList,true);
                    //recyclerView.setAdapter(adapter1);

                    ReservationListAdapter adapter2 = new ReservationListAdapter(reservationList,false);
                    //OBIRSATI OBAVEZNO KAD DOBAVIM NOV GET
                    recyclerViewGuest.setAdapter(adapter2);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ReservationDTO>> call, Throwable t) {
                Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
            }
        });

    }

    private void getDeleteRequests(){
        reservationList2 = new ArrayList<>();
        Call<ArrayList<ReservationDTO>> call = ServiceUtils.guestService.notAcceptedReservationByGuest(3L);
        Log.d("CALL", call.toString());
        call.enqueue(new Callback<ArrayList<ReservationDTO>>() {
            @Override
            public void onResponse(Call<ArrayList<ReservationDTO>> call, Response<ArrayList<ReservationDTO>> response) {
                if (response.code() == 200){
                    reservationDTOS2 = response.body();
                    for(ReservationDTO reservationDTO : reservationDTOS2){
                        reservationList2.add(new Reservation(reservationDTO));
                    }
                    ReservationListAdapter adapter1 = new ReservationListAdapter(reservationList2,true);
                    recyclerView.setAdapter(adapter1);

                }
            }

            @Override
            public void onFailure(Call<ArrayList<ReservationDTO>> call, Throwable t) {
                Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
            }
        });

    }

    private void searchRequests(String nameAccommodation, String selectedStatus) {
        adapter = new ReservationListAdapter(reservationList2,false);
        Call<ArrayList<ReservationDTO>> call = ServiceUtils.guestService.searchedRequests(selectedStatus,"","",nameAccommodation,3L);
        call.enqueue(new Callback<ArrayList<ReservationDTO>>() {
            @Override
            public void onResponse(Call<ArrayList<ReservationDTO>> call, Response<ArrayList<ReservationDTO>> response) {
                if (response.code() == 200){
                    Log.i("UDJE U RESPONSE BODY SEARCH",response.body().toString());
                    reservationDTOS2 = response.body();
                    reservationList2.clear();
                    for(ReservationDTO reservationDTO : reservationDTOS2){
                        reservationList2.add(new Reservation(reservationDTO));
                    }
                    if(adapter != null) {
                        adapter.notifyDataSetChanged();
                        adapter = new ReservationListAdapter(reservationList2);
                        recyclerViewGuest.setAdapter(adapter);
                    } else {
                        // Ako adapter nije prethodno postavljen, kreirajte ga i postavite
                        adapter = new ReservationListAdapter(reservationList2);
                        recyclerViewGuest.setAdapter(adapter);
                    }
                }
            }
            @Override
            public void onFailure(Call<ArrayList<ReservationDTO>> call, Throwable t) {
                Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
            }
        });
    }

    //gpt izgenerisao
    private String handleDateChange(CalendarView calendarView, int year, int month, int dayOfMonth) {

        String formattedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
        return formattedDate;
    }
}