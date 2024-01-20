package com.example.shopapp.activities.Notifications;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.shopapp.R;
import com.example.shopapp.adapters.NotificationAdapter;
import com.example.shopapp.configuration.ServiceUtils;
import com.example.shopapp.enums.NotificationStatus;
import com.example.shopapp.model.accommodation.Accommodation;
import com.example.shopapp.model.notifications.NotificationUserVisible;
import com.example.shopapp.model.user.Guest;
import com.example.shopapp.model.user.Owner;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GuestNotificationActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private NotificationAdapter adapter;
    private List<NotificationUserVisible> notificationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_notification2);

        recyclerView = findViewById(R.id.recyclerViewNotifications);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        notificationList = new ArrayList<>();

        Call<ArrayList<NotificationUserVisible>> call = ServiceUtils.notificationService.getAllGuest(3L);
        call.enqueue(new Callback<ArrayList<NotificationUserVisible>>() {
            @Override
            public void onResponse(Call<ArrayList<NotificationUserVisible>> call, Response<ArrayList<NotificationUserVisible>> response) {
                if (response.code() == 200){
                    System.out.println(response.body());
                    notificationList= response.body();
                    adapter = new NotificationAdapter(notificationList);
                    recyclerView.setAdapter(adapter);

                    for(NotificationUserVisible not: notificationList){
                        Call<Void> deleteNotCall = ServiceUtils.notificationService.deleteNotVisible(not.getId());

                        deleteNotCall.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if(response.isSuccessful()){
                                    return;
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {

                            }
                        });
                    }

                }
            }
            @Override
            public void onFailure(Call<ArrayList<NotificationUserVisible>> call, Throwable t) {
                Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
            }
        });


    }
}