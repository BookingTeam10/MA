package com.example.shopapp.adapters;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopapp.R;
import com.example.shopapp.configuration.ServiceUtils;
import com.example.shopapp.dto.ReservationDTO;
import com.example.shopapp.model.reservation.Reservation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservationListAdapter extends RecyclerView.Adapter<ReservationListAdapter.ReservationViewHolder> {

    private List<Reservation> reservationList;

    public ReservationListAdapter(List<Reservation> reservationList) {
        this.reservationList = reservationList;
    }

    public ReservationListAdapter(Context context, FragmentManager supportFragmentManager, ArrayList<Reservation> reservations){
//        super(context, R.layout.reservation_card, reservations);
        reservationList = reservations;
    }
    public static String getRole(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE);
        String role = sharedPreferences.getString("pref_role", "undefined");
        return role;
    }

    @Override
    public ReservationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reservation, parent, false);
        return new ReservationViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ReservationViewHolder holder, int position) {
        Reservation reservation = reservationList.get(position);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        holder.textViewId.setText((String.valueOf(reservation.getId())));
        holder.textViewAccommodation.setText((String.valueOf(reservation.getAccommodation())));
        if (reservation.getStartDate()==null){
            holder.textViewStart.setText((""));}
        else{
            holder.textViewStart.setText((String.valueOf(reservation.getStartDate())));}

        if (reservation.getEndDate()==null){
            holder.textViewEnd.setText((""));
        }else{
            holder.textViewEnd.setText((String.valueOf(reservation.getEndDate())));}
        if (reservation.getAccommodation()==null){
            holder.textViewAccommodation.setText((""));
        }else{
            holder.textViewAccommodation.setText((String.valueOf(reservation.getAccommodation())));}

        holder.buttonAction.setOnClickListener(view -> {
            int adapterPosition = holder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                deleteRequest(reservation,adapterPosition);
                //reservationList.remove(adapterPosition);
                //notifyItemRemoved(adapterPosition);
            }
        });
    }

    private void deleteRequest(Reservation reservation, int adapterPosition) {
        Call<ReservationDTO> call = ServiceUtils.guestService.deleteGuestReservation(reservation.getId());

        call.enqueue(new Callback<ReservationDTO>() {
            @Override
            public void onResponse(Call<ReservationDTO> call, Response<ReservationDTO> response) {
                if(!response.isSuccessful()){
                    //Toast.makeText( "Something went wrong!", Toast.LENGTH_SHORT).show();
                    return;
                }
                //Toast.makeText(getActivity(), "Successfully deleted favorite route", Toast.LENGTH_SHORT).show();
                reservationList.remove(adapterPosition);
                notifyItemRemoved(adapterPosition);
            }

            @Override
            public void onFailure(Call<ReservationDTO> call, Throwable t) {
                Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
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
            String role = getRole(view.getContext());
            textViewId = view.findViewById(R.id.textViewId);
            textViewStart = view.findViewById(R.id.textViewStart);
            textViewEnd = view.findViewById(R.id.textViewEnd);
            textViewAccommodation= view.findViewById(R.id.textViewAccommodation);
            buttonAction = view.findViewById(R.id.buttonAction);
            if(!role.equals("Guest")){
                buttonAction.setVisibility(View.INVISIBLE);
            }
        }
    }
}
