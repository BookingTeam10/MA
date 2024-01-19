package com.example.shopapp.activities.GuestScreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.shopapp.R;
import com.example.shopapp.activities.MapsActivity;
import com.example.shopapp.adapters.CommentsListAdapter;
import com.example.shopapp.adapters.MyReservationListAdapter;
import com.example.shopapp.adapters.ReservationListAdapter;
import com.example.shopapp.enums.NotificationStatus;
import com.example.shopapp.enums.ReviewStatus;
import com.example.shopapp.model.accommodation.Accommodation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopapp.R;
import com.example.shopapp.activities.MapsActivity;
import com.example.shopapp.configuration.ServiceUtils;
import com.example.shopapp.dto.ReservationDTO;
import com.example.shopapp.enums.ReservationStatus;
import com.example.shopapp.model.accommodation.Accommodation;
import com.example.shopapp.model.accommodation.Amenity;
import com.example.shopapp.model.notifications.NotificationUserVisible;
import com.example.shopapp.model.reservation.Reservation;
import com.example.shopapp.model.review.Review;
import com.example.shopapp.model.user.Guest;
import com.example.shopapp.model.user.Owner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccommodationDetailsScreen extends AppCompatActivity {

    ArrayList<ReservationDTO> reservationDTOS = new ArrayList<>();
    ArrayList<Reservation> reservations = new ArrayList<>();
    ArrayList<Review> reviews = new ArrayList<>();
    RecyclerView recyclerView = null;
    ReservationListAdapter adapter;
   private static String OWNER_CHANNEL = "Owner channel";
    String date1,date2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accommodation_details_screen);
        Accommodation accommodation = getIntent().getParcelableExtra("accommodation");
        TextView tvHotelName = findViewById(R.id.tvHotelName);
        //ImageView ivHotel = findViewById(R.id.ivHotel);
        TextView tvHotelDescription = findViewById(R.id.tvHotelDescription);
        TextView textViewAmenity = findViewById(R.id.textViewAmenity);
        TextView textViewPrice = findViewById(R.id.textViewPrice);
        tvHotelName.setText(accommodation.getName());
        CalendarView calendarViewStart;
        CalendarView calendarViewEnd;

        tvHotelDescription.setText(accommodation.getDescription());
        StringBuilder amenitiesBuilder = new StringBuilder();
        for (Amenity amenity : accommodation.getAmenities()) {
            amenitiesBuilder.append(amenity.getName()).append(", ");
        }
        if (amenitiesBuilder.length() > 0) {
            amenitiesBuilder.setLength(amenitiesBuilder.length() - 2);
        }
        textViewAmenity.setText("Amenity: " + amenitiesBuilder.toString());

        recyclerView = findViewById(R.id.recyclerViewComment);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        double price = 0;
        if (accommodation.getPrices() != null && !accommodation.getPrices().isEmpty()) {
            price = accommodation.getPrices().get(0).getPrice(); // Pretpostavka da postoji getPrice metoda
        }
        textViewPrice.setText("Cena za 1 noćenje je " + price);
        loadReservations(accommodation);

        Button mapButton = findViewById(R.id.buttonMapsAAA);
        Button buttonBook = findViewById(R.id.buttonBook);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccommodationDetailsScreen.this, MapsActivity.class);
                startActivity(intent);
            }
        });

        SharedPreferences sharedPreferences = this.getSharedPreferences("preferences", Context.MODE_PRIVATE);
        String role = sharedPreferences.getString("pref_role", "undefined");

        if (!role.equals("Guest")) {
            buttonBook.setVisibility(View.INVISIBLE);
        }
        double finalPrice = price;

        calendarViewStart = findViewById(R.id.calendarViewStart);
        calendarViewEnd = findViewById(R.id.calendarViewEnd);


        calendarViewStart.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                date1 = year + "-" + (month + 1) + "-" + dayOfMonth;

            }
        });
        calendarViewEnd.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                date2 = year + "-" + (month + 1) + "-" + dayOfMonth;
            }
        });
        buttonBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //prepraviti posle kada dobavimo gosta

                Call<ReservationDTO> call = ServiceUtils.guestService.createReservation(finalPrice,date1,date2, accommodation.getId(), 3L, accommodation.isAutomaticConfirmation());

                call.enqueue(new Callback<ReservationDTO>() {
                    @Override
                    public void onResponse(Call<ReservationDTO> call, Response<ReservationDTO> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(AccommodationDetailsScreen.this, "Request is created", Toast.LENGTH_SHORT).show();
                            String text = "Request is created for accommodation with ID = "+accommodation.getId()+", name = " + accommodation.getName();
                            NotificationUserVisible notificationUserVisible = new NotificationUserVisible(100L,text, NotificationStatus.NOT_VISIBLE,new Guest(3L),new Owner(1L),"today","GO");
                            createNotification(notificationUserVisible);
                            View view = findViewById(R.id.viewDetails);
                            sendOwner(view,text);
                            return;
                        }
                    }
                    @Override
                    public void onFailure(Call<ReservationDTO> call, Throwable t) {
                        Log.d("REZ", t.getMessage() != null ? t.getMessage() : "error");
                    }
                });
            }
        });
    }

    private void loadReservations(Accommodation accommodation){
        Call<ArrayList<ReservationDTO>> call = ServiceUtils.reservationService.getByAccommodations(accommodation.getId());
        call.enqueue(new Callback<ArrayList<ReservationDTO>>() {
            @Override
            public void onResponse(Call<ArrayList<ReservationDTO>> call, Response<ArrayList<ReservationDTO>> response) {
                if (response.code() == 200){
                    System.out.println(response.body());
                    reservationDTOS = response.body();
                    loadReviews();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<ReservationDTO>> call, Throwable t) {
                Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
            }
        });
    }
    private void loadReviews(){
        for(ReservationDTO reservationDTO:reservationDTOS){

            Call<Review> call = ServiceUtils.reviewService.getReviewByReservationId(reservationDTO.getId());
            call.enqueue(new Callback<Review>() {
                @Override
                public void onResponse(Call<Review> call, Response<Review> response) {
                    if (response.code() == 200){
                        System.out.println(response.body());
                        reviews.add(response.body());
                        CommentsListAdapter adapter = new CommentsListAdapter(reviews);
                        recyclerView.setAdapter(adapter);
                    }
                }
                @Override
                public void onFailure(Call<Review> call, Throwable t) {
                    Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
                }
            });
        }
    }
    private void createNotification(NotificationUserVisible notification){
            Call<NotificationUserVisible> call = ServiceUtils.notificationService.createNotification(notification.getText(),notification.getGuest().getId(),notification.getOwner().getId(),notification.getUserRate());
            call.enqueue(new Callback<NotificationUserVisible>() {
                @Override
                public void onResponse(Call<NotificationUserVisible> call, Response<NotificationUserVisible> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(AccommodationDetailsScreen.this, "Notification is send", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                @Override
                public void onFailure(Call<NotificationUserVisible> call, Throwable t) {
                    Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
                }
            });
    }

    public void sendOwner(View v,String message) {
        Log.d("UDJE OVDE DA POSALJE PORUKU","UDJEEE");
        Notification notification = new NotificationCompat.Builder(this,OWNER_CHANNEL)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Guest created request for accommodation")
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManagerCompat.notify(0, notification);

    }
}
