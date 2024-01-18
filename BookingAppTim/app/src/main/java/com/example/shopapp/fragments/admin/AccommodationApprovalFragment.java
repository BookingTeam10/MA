package com.example.shopapp.fragments.admin;

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
import com.example.shopapp.adapters.AccommodationTableAdapter;
import com.example.shopapp.configuration.ServiceUtils;
import com.example.shopapp.enums.AccommodationStatus;
import com.example.shopapp.model.accommodation.Accommodation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccommodationApprovalFragment extends Fragment implements AccommodationTableAdapter.OnButtonClickListener {

    private List<Accommodation> accommodations = new ArrayList<>();
    private AccommodationTableAdapter adapter;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_accommodation_approval, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);

//        adapter = new AccommodationTableAdapter(accommodations);
//        recyclerView.setAdapter(adapter);
//
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        loadAccommodations();
    }

    private void loadAccommodations() {

        Call<ArrayList<Accommodation>> accommodationCall = ServiceUtils.accommodationService.getAll();

        accommodationCall.enqueue(new Callback<ArrayList<Accommodation>>() {
            @Override
            public void onResponse(Call<ArrayList<Accommodation>> call, Response<ArrayList<Accommodation>> response) {
                if(response.isSuccessful()){
                    accommodations = response.body().stream()
                            .filter(accommodation -> accommodation.getAccommodationStatus() != AccommodationStatus.APPROVED && accommodation.getAccommodationStatus() != AccommodationStatus.REJECTED)
                            .collect(Collectors.toList());

                    adapter = new AccommodationTableAdapter(accommodations, AccommodationApprovalFragment.this);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                }


            }

            @Override
            public void onFailure(Call<ArrayList<Accommodation>> call, Throwable t) {

            }
        });




    }

    @Override
    public void onApproveClick(int position) {
        Accommodation acc = accommodations.get(position);
        Call<Void> callApprove = ServiceUtils.accommodationService.approveAccommodation(acc.getId(), acc);

        callApprove.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    loadAccommodations();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    @Override
    public void onRejectClick(int position) {
        Accommodation acc = accommodations.get(position);
        Call<Void> callReject = ServiceUtils.accommodationService.rejectAccommodation(acc.getId(), acc);

        callReject.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    loadAccommodations();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
}
