package com.example.shopapp.fragments.reviews;

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
import com.example.shopapp.dto.ReviewDTO;
import com.example.shopapp.enums.ReviewStatus;
import com.example.shopapp.model.reservation.Reservation;
import com.example.shopapp.model.review.Review;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddReviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddReviewFragment extends Fragment {

    Reservation reservation;

    public AddReviewFragment() {
        // Required empty public constructor
    }

    public static AddReviewFragment newInstance(String param1, String param2) {
        AddReviewFragment fragment = new AddReviewFragment();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            reservation = (Reservation) getArguments().getSerializable("reservation");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_review, container, false);
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

                    Log.d("SELECT VALUE",selectedValue);

                    Log.d("REZERVACIJA",reservation.toString());


                    Review review=new Review(100L,Double.parseDouble(selectedValue),text,ReviewStatus.PENDING,reservation);
                    Log.d("REVIEW DODATO",review.toString());
                    Call<ReviewDTO> call = ServiceUtils.reviewService.createReview(review);

                    call.enqueue(new Callback<ReviewDTO>() {
                        @Override
                        public void onResponse(Call<ReviewDTO> call, Response<ReviewDTO> response) {
                            Log.d("RESPONSE CODE AAA","A");
                            if (response.code() == 200){
                                Log.d("REZ","Meesage recieved");
                                System.out.println(response.body());
                                ReviewDTO product1 = response.body();
                                System.out.println(product1);
                                getActivity().getSupportFragmentManager().popBackStack();
                            }else{
                                Log.d("REZ","Meesage recieved: "+response.code());
                            }
                        }

                        @Override
                        public void onFailure(Call<ReviewDTO> call, Throwable t) {
                            Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
                        }
                    });


                }
            }
        });
    }
}