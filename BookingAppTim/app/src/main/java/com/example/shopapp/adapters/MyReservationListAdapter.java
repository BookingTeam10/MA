package com.example.shopapp.adapters;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopapp.R;
import com.example.shopapp.fragments.guest.myReservations.ReservationDetailFragment;
import com.example.shopapp.model.reservation.Reservation;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class MyReservationListAdapter extends RecyclerView.Adapter<ReservationListAdapter.ReservationViewHolder>{
    private List<Reservation> reservationList;
    private FragmentManager fragmentManager;
    private Activity activity;
    public MyReservationListAdapter(Activity context,List<Reservation> reservationList, FragmentManager fragmentManager) {
        this.reservationList = reservationList;
        activity=context;
        this.fragmentManager = fragmentManager;
    }
    @Override
    public ReservationListAdapter.ReservationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_reservation, parent, false);
        return new ReservationListAdapter.ReservationViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ReservationListAdapter.ReservationViewHolder holder, int position) {
        Reservation reservation = reservationList.get(position);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        holder.textViewId.setText((String.valueOf(reservation.getId())));
        holder.textViewAccommodation.setText((String.valueOf(reservation.getAccommodation())));
        if (reservation.getStartDate()==null){
            holder.textViewStart.setText((""));}
        else{
            System.out.println(reservation.getStartDate());
            String date=String.valueOf(reservation.getStartDate());
            String[] parts = date.split(" ");
            String day = parts[2];
            String month = parts[1];
            String year = parts[5];
            String formattedDate = day + " " + month + " " + year;
            holder.textViewStart.setText(formattedDate);
        }

        if (reservation.getEndDate()==null){
            holder.textViewEnd.setText((""));
        }else{
            System.out.println(reservation.getStartDate());
            String date=String.valueOf(reservation.getEndDate());
            String[] parts = date.split(" ");
            String day = parts[2];
            String month = parts[1];
            String year = parts[5];
            String formattedDate = day + " " + month + " " + year;
            holder.textViewEnd.setText(formattedDate);
            holder.textViewEnd.setText(formattedDate);}
        if (reservation.getAccommodation()==null){
            holder.textViewAccommodation.setText((""));
        }else{
            holder.textViewAccommodation.setText((String.valueOf(reservation.getAccommodation())));}

        holder.buttonAction.setOnClickListener(view -> {
            int adapterPosition = holder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                Reservation singleReservation=reservationList.get(adapterPosition);
                LayoutInflater inflater = LayoutInflater.from(activity);
                View viewPage = inflater.inflate(R.layout.fragment_reservation_detail, null);

                TextView productDescription = viewPage.findViewById(R.id.reservationDetails);

                if(singleReservation != null){
//                    FrameLayout mainContainer = activity.findViewById(R.id.frameLayoutContainer);
//                    if (mainContainer != null) {
//                        mainContainer.setVisibility(View.GONE);
//                    }
                    productDescription.setText(singleReservation.getAccommodation().toString());
                    ReservationDetailFragment reservationDetailsFragment = new ReservationDetailFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("reservation", singleReservation); // Pretpostavlja se da je 'accommodation' Serializable
                    reservationDetailsFragment.setArguments(bundle);
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayoutContainer, reservationDetailsFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }

            }
        });
    }


    @Override
    public int getItemCount() {
        return reservationList.size();
    }

    public static class ReservationViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewId, textViewStart, textViewEnd,textViewAccommodation;
        public Button buttonAction;

        public ReservationViewHolder(View view) {
            super(view);
            textViewId = view.findViewById(R.id.textViewId);
            textViewStart = view.findViewById(R.id.textViewStart);
            textViewEnd = view.findViewById(R.id.textViewEnd);
            textViewAccommodation= view.findViewById(R.id.textViewAccommodation);
            buttonAction = view.findViewById(R.id.buttonAction);
        }
    }
}
