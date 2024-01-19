package com.example.shopapp.fragments.guest.reviews;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Notification;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.shopapp.R;
import com.example.shopapp.activities.GuestScreen.AccommodationDetailsScreen;
import com.example.shopapp.configuration.ServiceUtils;
import com.example.shopapp.enums.NotificationStatus;
import com.example.shopapp.enums.ReviewStatus;
import com.example.shopapp.model.notifications.NotificationUserVisible;
import com.example.shopapp.model.reservation.Reservation;
import com.example.shopapp.model.review.ReviewOwner;
import com.example.shopapp.model.user.Guest;
import com.example.shopapp.model.user.Owner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddReviewOwnerFragment extends Fragment {

    Reservation reservation;
    private static String OWNER_CHANNEL = "Owner channel";

    public AddReviewOwnerFragment() {
        // Required empty public constructor
    }

    public static AddReviewOwnerFragment newInstance(String param1, String param2) {
        AddReviewOwnerFragment fragment = new AddReviewOwnerFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            reservation = (Reservation) getArguments().getSerializable("reservation");
            Log.d("A","A");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_review_owner, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        Button addRateOwnerButton = view.findViewById(R.id.btnAddRate);

        addRateOwnerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("RateOwnerButton", "Dugme rateOwner je kliknuto.");
                //view.setVisibility(View.GONE);
                EditText editTextDescribe = view.findViewById(R.id.editTextDescribe);
                String text = editTextDescribe.getText().toString();

                RadioGroup radioGroup = view.findViewById(R.id.RadioGroup);
                int selectedId = radioGroup.getCheckedRadioButtonId();
                if(selectedId==-1 || text.equals("")){
                    new AlertDialog.Builder(getContext())
                            .setTitle("Review Owner Unavailable")
                            .setMessage("Error!")
                            .setPositiveButton(android.R.string.ok, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }else{
                    RadioButton radioButton = view.findViewById(selectedId);
                    String selectedValue = radioButton.getText().toString();

                    Date currentDate=null;
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    try {
                        String formattedDate = sdf.format(new Date());
                        currentDate = sdf.parse(formattedDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                    ReviewOwner reviewOwner=new ReviewOwner(100L,Double.parseDouble(selectedValue),text, ReviewStatus.ACTIVE,null,reservation.getAccommodation().getOwner(), new Guest(3L),false);
                    Call<ReviewOwner> call = ServiceUtils.reviewService.addRate(reservation.getAccommodation().getOwner().getId(),3L,reviewOwner);

                    call.enqueue(new Callback<ReviewOwner>() {
                        @Override
                        public void onResponse(Call<ReviewOwner> call, Response<ReviewOwner> response) {
                            Log.d("RESEPONSE CODE DUGME MATIJA", String.valueOf(response.code()));
                            if (response.code() == 201){
                                Log.d("REZ","Meesage recieved");
                                ReviewOwner product1 = response.body();
                                createNotification(new NotificationUserVisible(100L,text, NotificationStatus.NOT_VISIBLE,new Guest(3L),new Owner(1L),"today","GO"));
                                sendOwner(view,text);
                                //getActivity().getSupportFragmentManager().popBackStack();
                            }else{
                                Log.d("REZ","Meesage recieved: "+response.code());
                            }
                        }

                        @Override
                        public void onFailure(Call<ReviewOwner> call, Throwable t) {
                            Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
                        }
                    });


                }
            }
        });
    }

    private void createNotification(NotificationUserVisible notification){
        Call<NotificationUserVisible> call = ServiceUtils.notificationService.createNotification(notification.getText(),notification.getGuest().getId(),notification.getOwner().getId(),notification.getUserRate());
        call.enqueue(new Callback<NotificationUserVisible>() {
            @Override
            public void onResponse(Call<NotificationUserVisible> call, Response<NotificationUserVisible> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getActivity(), "Notification is send", Toast.LENGTH_SHORT).show();
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
        Notification notification = new NotificationCompat.Builder(getActivity(),OWNER_CHANNEL)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Guest created request for accommodation")
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getActivity());
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
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