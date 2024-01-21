package com.example.shopapp.fragments.owner.reservations;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopapp.R;
import com.example.shopapp.adapters.OwnerReservationListAdapter;
import com.example.shopapp.configuration.ServiceUtils;
import com.example.shopapp.model.reservation.Reservation;
import com.example.shopapp.model.user.Owner;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OwnerReservationsFragment extends Fragment implements OwnerReservationListAdapter.OnButtonClickListener {

    private List<Reservation> reservations;
    private Owner owner;
    private OwnerReservationListAdapter adapter;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_owner_reservations, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);

        loadReservations();
    }

    private void loadReservations() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            owner = bundle.getParcelable("owner");
        }

        Call<ArrayList<Reservation>> reservationsCall = ServiceUtils.reservationService.getOwnerReservaitons(owner.getId());

        reservationsCall.enqueue(new Callback<ArrayList<Reservation>>() {
            @Override
            public void onResponse(Call<ArrayList<Reservation>> call, Response<ArrayList<Reservation>> response) {
                if(response.isSuccessful()){
                    reservations = response.body();
                    adapter = new OwnerReservationListAdapter(reservations, OwnerReservationsFragment.this);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Reservation>> call, Throwable t) {

            }
        });

    }


    @Override
    public void onDeleteClick(int position) {
        Reservation r = reservations.get(position);

        Call<Void> rejectCall = ServiceUtils.reservationService.cancelReservation(r.getId(), new Reservation(), true);

        rejectCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    loadReservations();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    @Override
    public void onApproveClick(int position) {
        Reservation r = reservations.get(position);

        Call<Void> acceptCall = ServiceUtils.reservationService.acceptReservation(r.getId(), new Reservation());

        acceptCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    loadReservations();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    @Override
    public void onCancelClick(int position) {

    }
}
