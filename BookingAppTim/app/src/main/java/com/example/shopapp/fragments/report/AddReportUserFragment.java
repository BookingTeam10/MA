package com.example.shopapp.fragments.report;

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

import com.example.shopapp.R;
import com.example.shopapp.configuration.ServiceUtils;
import com.example.shopapp.enums.ReviewStatus;
import com.example.shopapp.model.reservation.Reservation;
import com.example.shopapp.model.user.Guest;
import com.example.shopapp.model.user.ReportUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddReportUserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddReportUserFragment extends Fragment {

    Reservation reservation;


    public AddReportUserFragment() {
        // Required empty public constructor
    }

    public static AddReportUserFragment newInstance(String param1, String param2) {
        AddReportUserFragment fragment = new AddReportUserFragment();
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
            Log.d("A","A");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_report_user, container, false);
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

                if(text.equals("")){
                    new AlertDialog.Builder(getContext())
                            .setTitle("Report Owner Unavailable")
                            .setMessage("Error!")
                            .setPositiveButton(android.R.string.ok, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }else{


                    ReportUser reportUser=new ReportUser(100L,text,ReviewStatus.ACTIVE,reservation.getAccommodation().getOwner(), new Guest(3L),"GO");
                    Call<ReportUser> call = ServiceUtils.reportService.addReport(reservation.getAccommodation().getOwner().getId(),3L,reportUser);

                    call.enqueue(new Callback<ReportUser>() {
                        @Override
                        public void onResponse(Call<ReportUser> call, Response<ReportUser> response) {
                            if (response.code() == 200){
                                Log.d("REZ","Meesage recieved");
                                System.out.println(response.body());
                                ReportUser product1 = response.body();
                                System.out.println(product1);
                                getActivity().getSupportFragmentManager().popBackStack();
                            }else{
                                Log.d("REZ","Meesage recieved: "+response.code());
                            }
                        }

                        @Override
                        public void onFailure(Call<ReportUser> call, Throwable t) {
                            Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
                        }
                    });


                }
            }
        });
    }
}