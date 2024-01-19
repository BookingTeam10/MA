package com.example.shopapp.activities.Notifications;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.shopapp.R;
import com.example.shopapp.adapters.NotificationAdapter;
import com.example.shopapp.configuration.ServiceUtils;
import com.example.shopapp.model.notifications.NotificationUserVisible;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OwnerNotificationActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private NotificationAdapter adapter;
    private List<NotificationUserVisible> notificationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_notification);
        recyclerView = findViewById(R.id.recyclerViewNotifications);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        notificationList = new ArrayList<>();

        Call<ArrayList<NotificationUserVisible>> call = ServiceUtils.notificationService.getAllOwner(1L);
        call.enqueue(new Callback<ArrayList<NotificationUserVisible>>() {
            @Override
            public void onResponse(Call<ArrayList<NotificationUserVisible>> call, Response<ArrayList<NotificationUserVisible>> response) {
                if (response.code() == 200){
                    System.out.println(response.body());
                    notificationList= response.body();
                    adapter = new NotificationAdapter(notificationList);
                    recyclerView.setAdapter(adapter);

                }
            }
            @Override
            public void onFailure(Call<ArrayList<NotificationUserVisible>> call, Throwable t) {
                Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
            }
        });

    }
}