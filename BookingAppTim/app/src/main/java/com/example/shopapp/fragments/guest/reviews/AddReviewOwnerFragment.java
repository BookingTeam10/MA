package com.example.shopapp.fragments.guest.reviews;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.shopapp.R;
import com.example.shopapp.configuration.ServiceUtils;
import com.example.shopapp.enums.ReviewStatus;
import com.example.shopapp.model.reservation.Reservation;
import com.example.shopapp.model.review.ReviewOwner;
import com.example.shopapp.model.user.Guest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddReviewOwnerFragment extends Fragment {

    Reservation reservation;

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
                            if (response.code() == 200){
                                Log.d("REZ","Meesage recieved");
                                System.out.println(response.body());
                                ReviewOwner product1 = response.body();
                                System.out.println(product1);
                                getActivity().getSupportFragmentManager().popBackStack();
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
}