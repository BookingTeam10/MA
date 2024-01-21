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
import com.example.shopapp.adapters.OwnerRateReportAdapter;
import com.example.shopapp.configuration.ServiceUtils;
import com.example.shopapp.enums.ReviewStatus;
import com.example.shopapp.model.review.Review;
import com.example.shopapp.model.review.ReviewOwner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OwnerRateReportFragment extends Fragment  implements  OwnerRateReportAdapter.OnButtonClickListener{
    private List<ReviewOwner> reports = new ArrayList<>();
    private OwnerRateReportAdapter adapter;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_owner_rate_report, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);

        loadReports();
    }

    private void loadReports(){
        Call<ArrayList<ReviewOwner>> accReportCall = ServiceUtils.reviewService.getAllOwnerReviews();

        accReportCall.enqueue(new Callback<ArrayList<ReviewOwner>>() {
            @Override
            public void onResponse(Call<ArrayList<ReviewOwner>> call, Response<ArrayList<ReviewOwner>> response) {
                if(response.isSuccessful()){
                    reports = response.body().stream().filter(accReport -> accReport.getStatus() != ReviewStatus.ACTIVE).collect(Collectors.toList());

                    adapter = new OwnerRateReportAdapter(reports, OwnerRateReportFragment.this);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ReviewOwner>> call, Throwable t) {

            }
        });

    }

    @Override
    public void onDeleteClick(int position) {
        ReviewOwner review = reports.get(position);
        review.setStatus(ReviewStatus.DELETED);

        Call<Void> deleteReviewCall = ServiceUtils.reviewService.updateReviewOwner(review.getId(), review, false);

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

        ReviewOwner review = reports.get(position);
        review.setStatus(ReviewStatus.ACTIVE);



        Call<Void> approveReviewCall = ServiceUtils.reviewService.updateReviewOwner(review.getId(), review, true);

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
