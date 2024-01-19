package com.example.shopapp.fragments.owner.requests;

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
import com.example.shopapp.model.user.Owner;

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
    String date1 = "";
    String date2="";


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
        Bundle bundle = getArguments();
        Owner owner = bundle.getParcelable("owner");
        Log.d("NASAO OWNER USER ACCOMMODATION LISt",owner.toString());
        CalendarView calendarView1 = view.findViewById(R.id.calendarViewForSearch12);
        CalendarView calendarView2 = view.findViewById(R.id.calendarViewForSearch22);

        calendarView1.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                // Implement your logic for calendarView1 date change here
                // The month value is 0-based (i.e., 0 for January)
                Log.i("UDJE OVDE1","UDJE OVDE1");
                date1 = handleDateChange(calendarView1, year, month, dayOfMonth);
            }
        });
        Log.i("UDJE OVDE2",date1);
        calendarView2.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                // Implement your logic for calendarView2 date change here
                date2 = handleDateChange(calendarView2, year, month, dayOfMonth);
                Log.i("UDJE OVDE3",date2);
            }
        });

        Log.d("DATUMIIII1",date1);

        getOwnersRequest(owner.getId());
        buttonSearch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.i("UDjE OVDE","UDJE OVDE");
                String selectedStatus = spinnerRequestStatus.getSelectedItem().toString();
               searchRequests(nameAccommodation,selectedStatus,date1,date2);
            }
        });

        return view;
    }

    private void getOwnersRequest(Long id){
        reservationList = new ArrayList<>();
        //ovo ispraviti, treba da bude za ownera
        Call<ArrayList<ReservationDTO>> call = ServiceUtils.ownerService.getOwnersRequests(id);
        call.enqueue(new Callback<ArrayList<ReservationDTO>>() {
            @Override
            public void onResponse(Call<ArrayList<ReservationDTO>> call, Response<ArrayList<ReservationDTO>> response) {
                if (response.code() == 200){
                    reservationDTOS = response.body();
                    for(ReservationDTO reservationDTO : reservationDTOS){
                        reservationList.add(new Reservation(reservationDTO));
                    }
                    //ReservationListAdapter adapter = new ReservationListAdapter(reservationList);
                    adapter = new ReservationListAdapter(reservationList);
                    recyclerView.setAdapter(adapter);

                }
            }

            @Override
            public void onFailure(Call<ArrayList<ReservationDTO>> call, Throwable t) {
                Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
            }
        });
    }
    private void searchRequests(String nameAccommodation, String selectedStatus,String date1,String date2) {
        Log.i("CITANJE IZ COMBOBOXA",selectedStatus);
        Log.i("CITANJE IZ TEXTBOXA",nameAccommodation);
        Call<ArrayList<ReservationDTO>> call = ServiceUtils.ownerService.searchedRequests(selectedStatus,date1,date2,nameAccommodation,1L);
        call.enqueue(new Callback<ArrayList<ReservationDTO>>() {
            @Override
            public void onResponse(Call<ArrayList<ReservationDTO>> call, Response<ArrayList<ReservationDTO>> response) {
                if (response.code() == 200){
                    Log.i("UDJE U RESPONSE BODY SEARCH",response.body().toString());
                    reservationDTOS = response.body();
                    reservationList.clear();
                    for(ReservationDTO reservationDTO : reservationDTOS){
                        reservationList.add(new Reservation(reservationDTO));
                    }
                    Log.i("UDJE U RESPONSE BODY2 SEARCH", String.valueOf(reservationDTOS.size()));
                    Log.i("UDJE U RESPONSE BODY2 SEARCH",reservationDTOS.toString());
                    if(adapter != null) {
                        adapter.notifyDataSetChanged();
                    } else {
                        // Ako adapter nije prethodno postavljen, kreirajte ga i postavite
                        adapter = new ReservationListAdapter(reservationList);
                        recyclerView.setAdapter(adapter);
                    }
                }
            }
            @Override
            public void onFailure(Call<ArrayList<ReservationDTO>> call, Throwable t) {
                Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
            }
        });
    }

    private String handleDateChange(CalendarView calendarView, int year, int month, int dayOfMonth) {

        String formattedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
        return formattedDate;
    }
}