package com.example.shopapp.activities.HostScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import com.example.shopapp.R;
import com.example.shopapp.adapters.MyReservationListAdapter;
import com.example.shopapp.adapters.ReservationListAdapter;
import com.example.shopapp.configuration.ServiceUtils;
import com.example.shopapp.model.reservation.Reservation;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OwnerReservationActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ReservationListAdapter adapter;
    private List<Reservation>  reservationList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_reservation);

        recyclerView = findViewById(R.id.recyclerViewReservations);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        reservationList = new ArrayList<>();

        Call<ArrayList<Reservation>> call = ServiceUtils.reservationService.getReservationsByOwner(1L);
        call.enqueue(new Callback<ArrayList<Reservation>>() {
            @Override
            public void onResponse(Call<ArrayList<Reservation>> call, Response<ArrayList<Reservation>> response) {
                if (response.code() == 200){
                    System.out.println(response.body());
                    reservationList = response.body();
                    adapter = new ReservationListAdapter(reservationList,getSupportFragmentManager()); // Inicijalizujte vaš adapter kako je potrebno
                    recyclerView.setAdapter(adapter);
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Reservation>> call, Throwable t) {
                Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
            }
        });

        // Dohvatite referencu na vaš adapter (adapter mora biti već kreiran)

    }
}