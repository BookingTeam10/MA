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
import com.example.shopapp.adapters.AccommodationRateTableAdapter;
import com.example.shopapp.configuration.ServiceUtils;
import com.example.shopapp.enums.ReviewStatus;
import com.example.shopapp.model.review.Review;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccommodationRateReportFragment extends Fragment implements AccommodationRateTableAdapter.OnButtonClickListener{

    private List<Review> reports = new ArrayList<>();
    private AccommodationRateTableAdapter adapter;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_acc_rate_report, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);

        loadReports();
    }

    private void loadReports(){
        Call<ArrayList<Review>> accReportCall = ServiceUtils.reviewService.getAllAccReviews();

        accReportCall.enqueue(new Callback<ArrayList<Review>>() {
            @Override
            public void onResponse(Call<ArrayList<Review>> call, Response<ArrayList<Review>> response) {
                if(response.isSuccessful()){
                    reports = response.body().stream().filter(accReport -> accReport.getStatus() != ReviewStatus.ACTIVE).collect(Collectors.toList());

                    adapter = new AccommodationRateTableAdapter(reports, AccommodationRateReportFragment.this);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Review>> call, Throwable t) {

            }
        });

    }

    @Override
    public void onDeleteClick(int position) {
        Review review = reports.get(position);

        Call<Void> deleteReviewCall = ServiceUtils.reviewService.deleteReview(review.getId());

        deleteReviewCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    loadReports();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    @Override
    public void onApproveClick(int position) {

        //OVDE NOTIFIKACIJE

        Review review = reports.get(position);



        Call<Void> approveReviewCall = ServiceUtils.reviewService.approveReview(review.getId(), new Review());

        approveReviewCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    loadReports();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
}
