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
import com.example.shopapp.adapters.UserReportTableAdapter;
import com.example.shopapp.configuration.ServiceUtils;
import com.example.shopapp.enums.ReviewStatus;
import com.example.shopapp.model.review.UserReport;
import com.example.shopapp.model.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserReportsAdminFragment extends Fragment implements UserReportTableAdapter.OnButtonClickListener {

    private List<UserReport> userReports = new ArrayList<>();
    private UserReportTableAdapter adapter;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_report_table, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);

        loadReports();
    }

    private void loadReports() {
        Call<ArrayList<UserReport>> userReportCall = ServiceUtils.userReportService.getAll();

        userReportCall.enqueue(new Callback<ArrayList<UserReport>>() {
            @Override
            public void onResponse(Call<ArrayList<UserReport>> call, Response<ArrayList<UserReport>> response) {
                if(response.isSuccessful()){
                    userReports = response.body().stream().filter(userReport ->
                       userReport.getStatus() == ReviewStatus.ACTIVE).collect(Collectors.toList());

                    adapter = new UserReportTableAdapter(userReports, UserReportsAdminFragment.this);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                }
            }

            @Override
            public void onFailure(Call<ArrayList<UserReport>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onDeleteClick(int position) {
        UserReport report = userReports.get(position);

        Call<Void> deleteReportCall = ServiceUtils.userReportService.delete(report.getId());

        deleteReportCall.enqueue(new Callback<Void>() {
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
    public void onBlockClick(int position) {
        UserReport report = userReports.get(position);
        Call<Void> blockUserCall;
        if(report.getUserReportUser().equalsIgnoreCase("go")) {
             blockUserCall = ServiceUtils.userService.blockUser(report.getOwner().getId(), report);
        }else{
             blockUserCall = ServiceUtils.userService.blockUser(report.getGuest().getId(), report);
        }

            blockUserCall.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if(response.isSuccessful()){
                        onDeleteClick(position);
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });
    }
}
