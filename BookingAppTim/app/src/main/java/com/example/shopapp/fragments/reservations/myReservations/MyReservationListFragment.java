package com.example.shopapp.fragments.reservations.myReservations;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.shopapp.R;
import com.example.shopapp.adapters.AccomodationListAdapter;
import com.example.shopapp.adapters.ReservationListAdapter;
import com.example.shopapp.configuration.ServiceUtils;
import com.example.shopapp.databinding.FragmentProductsListBinding;
import com.example.shopapp.fragments.accomodations.AccomodationPageViewModel;
import com.example.shopapp.fragments.accomodations.AccomodationsListFragment;
import com.example.shopapp.model.accommodation.Accommodation;
import com.example.shopapp.model.reservation.Reservation;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyReservationListFragment extends Fragment {

    private RecyclerView recyclerView;
    private ReservationListAdapter adapter;
    private MyReservationPageViewModel productsViewModel;
    private ArrayList<Reservation> reservations = new ArrayList<>();

    public static MyReservationListFragment newInstance(ArrayList<Reservation> reservations){
        MyReservationListFragment fragment = new MyReservationListFragment();
        //Bundle args = new Bundle();
        //args.putParcelableArrayList("reservations", reservations);
        //fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_reservation_list_list, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewRes);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        Log.d("REZ REPO SMRDLJIVI","Meesage recieved");

        //Log.d("GOST ID",);


        Call<ArrayList<Reservation>> call = ServiceUtils.reservationService.getGuestsRequests();

        Log.d("REZ REPO SMRDLJIVI234","Meesage recieved");

        call.enqueue(new Callback<ArrayList<Reservation>>() {
            @Override
            public void onResponse(Call<ArrayList<Reservation>> call, Response<ArrayList<Reservation>> response) {
                if (response.code() == 200){
                    Log.d("REZ REPO","Meesage recieved");
                    System.out.println(response.body());
                    Log.d("REZ11","Meesage recieved");
                    reservations = response.body();
                    Log.d("REZ1","Meesage recieved");
                    ReservationListAdapter adapter = new ReservationListAdapter(reservations);
                    Log.d("REZ2","Meesage recieved");
                    recyclerView.setAdapter(adapter);
                    Log.d("REZ3","Meesage recieved");
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Reservation>> call, Throwable t) {
                Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //getDataFromClient();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}