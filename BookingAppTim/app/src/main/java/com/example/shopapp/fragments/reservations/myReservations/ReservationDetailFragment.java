package com.example.shopapp.fragments.reservations.myReservations;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shopapp.R;
import com.example.shopapp.adapters.MyReservationListAdapter;
import com.example.shopapp.configuration.ServiceUtils;
import com.example.shopapp.fragments.reservations.RequestFragment;
import com.example.shopapp.fragments.reviews.AddReviewOwnerFragment;
import com.example.shopapp.fragments.reviews.ViewReviewOwnerFragment;
import com.example.shopapp.model.accommodation.Accommodation;
import com.example.shopapp.model.reservation.Reservation;
import com.example.shopapp.model.review.Review;
import com.example.shopapp.model.review.ReviewOwner;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReservationDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReservationDetailFragment extends Fragment {

    private Reservation reservation;

    private ReviewOwner reviewOwner;

    private FragmentManager fragmentManager;
    public ReservationDetailFragment() {
        // Required empty public constructor
    }

    public static ReservationDetailFragment newInstance(String param1, String param2) {
        ReservationDetailFragment fragment = new ReservationDetailFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            reservation = (Reservation) getArguments().getSerializable("reservation");
            Log.d("UCITA SE LEPO",reservation.getAccommodation().getName());
            //reservationDetails.set
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Log.d("REZERVACIJA ID ACC",reservation.getAccommodation().getOwner().getId().toString());

        Call<ReviewOwner> call = ServiceUtils.reviewService.getReviewOwnerByReservation(reservation.getAccommodation().getOwner().getId(),3L);

        call.enqueue(new Callback<ReviewOwner>() {
            @Override
            public void onResponse(Call<ReviewOwner> call, Response<ReviewOwner> response) {
                if (response.code() == 200){
                    System.out.println(response.body());
                    reviewOwner = response.body();
                    //MyReservationListAdapter adapter = new MyReservationListAdapter(getActivity(),reservations, getChildFragmentManager());
                    //recyclerView.setAdapter(adapter);
                }
            }
            @Override
            public void onFailure(Call<ReviewOwner> call, Throwable t) {
                Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
            }
        });

        return inflater.inflate(R.layout.fragment_reservation_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tvHotelName = view.findViewById(R.id.tvHotelName);
        TextView reservationDetails = view.findViewById(R.id.reservationDetails);

        String describe="";

        if (reservation != null) {
            Accommodation accommodation = reservation.getAccommodation();

            String dateBegin=String.valueOf(reservation.getStartDate());
            String[] parts1 = dateBegin.split(" ");
            String day = parts1[2];
            String month = parts1[1];
            String year = parts1[5];
            String formattedDateBegin = day + " " + month + " " + year;

            String dateEnd=String.valueOf(reservation.getEndDate());
            String[] parts2 = dateEnd.split(" ");
            String day2 = parts2[2];
            String month2 = parts2[1];
            String year2 = parts2[5];
            String formattedDateEnd = day2 + " " + month2 + " " + year2;

            if (accommodation != null) {
                tvHotelName.setText(accommodation.getName());
                describe=describe+formattedDateBegin+"\n"+formattedDateEnd+"\n"+reservation.getAccommodation().getName()+"\n"+reservation.getAccommodation().getDescription()+"\n";
                reservationDetails.setText(describe);
            }
        }

        Button rateOwnerButton = view.findViewById(R.id.rateOwner);

        Button deleterateOwnerButton = view.findViewById(R.id.deleteRateOwner);

        Button viewRateOwnerButton = view.findViewById(R.id.viewRateOwner);

        Button reportOwnerButton = view.findViewById(R.id.reportOwner);

        rateOwnerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("RateOwnerButton", "Dugme rateOwner je kliknuto.");
                view.setVisibility(View.GONE);
                if(reviewOwner!=null){
                    new AlertDialog.Builder(getContext())
                            .setTitle("Review Owner Unavailable")
                            .setMessage("You cannot rate the owner at this moment, because you have rated owner.")
                            .setPositiveButton(android.R.string.ok, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }else{
                    AddReviewOwnerFragment fragment = new AddReviewOwnerFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("reservation", reservation);
                    fragment.setArguments(bundle);
                    FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                    //transaction.replace(R.id.reservation_detail, fragment);
                    transaction.replace(R.id.frameLayoutContainer, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }
        });

        deleterateOwnerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("RateOwnerButton", "Dugme rateOwner je kliknuto.");
                view.setVisibility(View.GONE);
                if(reviewOwner==null){
                    new AlertDialog.Builder(getContext())
                            .setTitle("Review Owner Unavailable")
                            .setMessage("You cannot delete rate the owner at this moment, because you haven't rated owner.")
                            .setPositiveButton(android.R.string.ok, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }else{
                    Call<ResponseBody> call = ServiceUtils.reviewService.deleteById(reviewOwner.getOwner().getId(),3L);
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.code() == 200){
                                Log.d("ShopApp","Meesage recieved");
                                System.out.println(response.body());
                                NavController navController = Navigation.findNavController(getActivity(), R.id.fragment_nav_content_main);
                                navController.navigate(R.id.nav_products);
                            }else{
                                Log.d("ShopApp","Meesage recieved: "+response.code());
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Log.d("ShopApp", t.getMessage() != null?t.getMessage():"error");
                        }
                    });
                }
            }
        });

        viewRateOwnerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("RateOwnerButton", "Dugme rateOwner je kliknuto.");
                view.setVisibility(View.GONE);
                if(reviewOwner==null){
                    new AlertDialog.Builder(getContext())
                            .setTitle("Review Owner Unavailable")
                            .setMessage("You cannot view rate the owner at this moment, because you haven't rated owner.")
                            .setPositiveButton(android.R.string.ok, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }else{
                    ViewReviewOwnerFragment fragment = new ViewReviewOwnerFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("reviewOwner", reviewOwner);
                    fragment.setArguments(bundle);
                    FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                    //transaction.replace(R.id.reservation_detail, fragment);
                    transaction.replace(R.id.frameLayoutContainer, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }
        });
    }
}