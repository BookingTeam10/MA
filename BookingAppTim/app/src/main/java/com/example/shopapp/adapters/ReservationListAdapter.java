package com.example.shopapp.adapters;

import static java.security.AccessController.getContext;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopapp.R;
import com.example.shopapp.configuration.ServiceUtils;
import com.example.shopapp.dto.ReservationDTO;
import com.example.shopapp.fragments.guest.report.AddReportUserFragment;
import com.example.shopapp.fragments.guest.reservations.myReservations.ReservationDetailFragment;
import com.example.shopapp.fragments.owner.report.AddReportOwnerFragment;
import com.example.shopapp.model.reservation.Reservation;
import com.example.shopapp.model.user.Owner;
import com.example.shopapp.model.user.ReportUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservationListAdapter extends RecyclerView.Adapter<ReservationListAdapter.ReservationViewHolder> {

    private List<Reservation> reservationList;
    private boolean showButton;

    private ReportUser reportUser;

    private FragmentManager fragmentManager;

    public ReservationListAdapter(List<Reservation> reservationList) {
        this.reservationList = reservationList;
    }

    public ReservationListAdapter(List<Reservation> reservationList, FragmentManager fragmentManager) {
        this.reservationList = reservationList;
        this.fragmentManager = fragmentManager;
    }

    public ReservationListAdapter(List<Reservation> reservationList, boolean showButton) {
        this.reservationList = reservationList;
        this.showButton = showButton;
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
        holder.textViewStatus.setText((String.valueOf(reservation.getStatus())));
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
            }
        });

        if(!showButton){
            holder.buttonAction.setVisibility(View.INVISIBLE);
        }

        holder.buttonReport.setOnClickListener(view -> {
            int adapterPosition = holder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                Reservation singleReservation=reservationList.get(adapterPosition);

                Log.d("MARAMICA",singleReservation.toString());


                Call<ReportUser> call = ServiceUtils.reportService.getUserReportOG(1L,singleReservation.getGuest().getId());

                call.enqueue(new Callback<ReportUser>() {
                    @Override
                    public void onResponse(Call<ReportUser> call, Response<ReportUser> response) {
                        Log.d("VRATI REPORT USER", String.valueOf(response.code()));
                        if(response.code() == 200){
                            Log.d("VRATI REPORT USER123","VRATI REPORT USER123");
                            System.out.println(response.body());
                            reportUser = response.body();
                            if(reportUser.getId().equals(500L)){
                                Log.d("POSTUJES","POSTUJES");
                                Log.d("REZERVACIJA ID",reservation.toString());
                                if(reservation.getEndDate()==null){
                                    new AlertDialog.Builder(view.getContext())
                                            .setTitle("Report Owner Unavailable")
                                            .setMessage("You cannot report the owner, because the reservation hasn't finished.")
                                            .setPositiveButton(android.R.string.ok, null)
                                            .setIcon(android.R.drawable.ic_dialog_alert)
                                            .show();
                                    return;
                                }
//                                if(reservation.getEndDate().after(new Date())){
//                                    new AlertDialog.Builder(view.getContext())
//                                            .setTitle("Report Owner Unavailable")
//                                            .setMessage("You cannot report the owner, because the reservation hasn't finished.")
//                                            .setPositiveButton(android.R.string.ok, null)
//                                            .setIcon(android.R.drawable.ic_dialog_alert)
//                                            .show();
//                                    return;
//                                }
                                Log.d("UDJE U OVO 100%","UDJE U OVO 100%");
                                AddReportOwnerFragment fragment = new AddReportOwnerFragment();
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("reservation", reservation);
                                fragment.setArguments(bundle);
                                FragmentTransaction transaction = fragmentManager.beginTransaction();
                                //transaction.replace(R.id.reservation_detail, fragment);
                                transaction.replace(R.id.Nesto, fragment);
                                transaction.addToBackStack(null);
                                transaction.commit();
                            }else{
                                new AlertDialog.Builder(view.getContext())
                                        .setTitle("Report Guest Unavailable")
                                        .setMessage("You cannot report the owner, because you have reported owner.")
                                        .setPositiveButton(android.R.string.ok, null)
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .show();
                            }
                        }
                        //Toast.makeText(getActivity(), "Successfully deleted favorite route", Toast.LENGTH_SHORT).show();
                        //reservationList.remove(adapterPosition);
                        //notifyItemRemoved(adapterPosition);
                    }

                    @Override
                    public void onFailure(Call<ReportUser> call, Throwable t) {
                        Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
                    }
                });


//                if(reportUser==null){
//                    Log.d("POSTUJES","POSTUJES");
//                    if(reservation.getEndDate()==null){
//                        new AlertDialog.Builder(view.getContext())
//                                .setTitle("Report Owner Unavailable")
//                                .setMessage("You cannot report the owner, because the reservation hasn't finished.")
//                                .setPositiveButton(android.R.string.ok, null)
//                                .setIcon(android.R.drawable.ic_dialog_alert)
//                                .show();
//                        return;
//                    }
//                    if(reservation.getEndDate().after(new Date())){
//                        new AlertDialog.Builder(view.getContext())
//                                .setTitle("Report Owner Unavailable")
//                                .setMessage("You cannot report the owner, because the reservation hasn't finished.")
//                                .setPositiveButton(android.R.string.ok, null)
//                                .setIcon(android.R.drawable.ic_dialog_alert)
//                                .show();
//                        return;
//                    }
//                    AddReportUserFragment fragment = new AddReportUserFragment();
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("reservation", reservation);
//                    fragment.setArguments(bundle);
//                    FragmentTransaction transaction = fragmentManager.beginTransaction();
//                    //transaction.replace(R.id.reservation_detail, fragment);
//                    transaction.replace(R.id.frameLayoutContainer, fragment);
//                    transaction.addToBackStack(null);
//                    transaction.commit();
//                }else{
//                    new AlertDialog.Builder(view.getContext())
//                            .setTitle("Report Guest Unavailable")
//                            .setMessage("You cannot report the owner, because you have reported owner.")
//                            .setPositiveButton(android.R.string.ok, null)
//                            .setIcon(android.R.drawable.ic_dialog_alert)
//                            .show();
//                }
            }
        });

//        holder.buttonReport.setOnClickListener(view -> {
//        });
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

        public TextView textViewId, textViewStart, textViewEnd,textViewAccommodation,textViewStatus;
        public Button buttonAction,buttonReport;

        public ReservationViewHolder(View view) {
            super(view);
            String role = getRole(view.getContext());
            textViewId = view.findViewById(R.id.textViewId);
            textViewStart = view.findViewById(R.id.textViewStart);
            textViewEnd = view.findViewById(R.id.textViewEnd);
            textViewAccommodation= view.findViewById(R.id.textViewAccommodation);
            textViewStatus= view.findViewById(R.id.textViewStatus);
            buttonAction = view.findViewById(R.id.buttonAction);
            buttonReport = view.findViewById(R.id.buttonNEPREKLAPASEID);
            if(!role.equals("Guest")){
                buttonAction.setVisibility(View.INVISIBLE);
            }
        }
    }
}

