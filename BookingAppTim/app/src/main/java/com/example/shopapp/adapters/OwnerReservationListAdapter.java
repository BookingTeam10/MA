package com.example.shopapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopapp.R;
import com.example.shopapp.enums.ReviewStatus;
import com.example.shopapp.model.reservation.Reservation;
import com.example.shopapp.model.review.Review;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OwnerReservationListAdapter extends RecyclerView.Adapter<OwnerReservationListAdapter.ViewHolder> {

    private List<Reservation> reservations;
    private OwnerReservationListAdapter.OnButtonClickListener listener;
    public boolean isGuest = false;

    public interface OnButtonClickListener {
        void onDeleteClick(int position);
        void onApproveClick(int position);

        void onCancelClick(int position);
    }


    public OwnerReservationListAdapter(List<Reservation> reservations, OwnerReservationListAdapter.OnButtonClickListener listener){
        this.reservations =reservations;
        this.listener = listener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_owner_reservation, parent, false);
        return new OwnerReservationListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Reservation r = reservations.get(position);
        holder.bind(r);
    }

    @Override
    public int getItemCount() {
        return reservations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView reservationId;
        private TextView startDate;
        private TextView endDate;
        private TextView accId;
        private TextView price;
        private Button btnDeleteReview, cancelRes;
        private Button btnApproveReview;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            reservationId = itemView.findViewById(R.id.reservationId);
            startDate = itemView.findViewById(R.id.startDate);
            endDate = itemView.findViewById(R.id.endDate);
            accId = itemView.findViewById(R.id.accId);
            price = itemView.findViewById(R.id.price);
            btnDeleteReview = itemView.findViewById(R.id.btnDelete);
            btnApproveReview = itemView.findViewById(R.id.btnApprove);
            cancelRes = itemView.findViewById(R.id.cancelRes);

        }


        public void bind(Reservation reservation) {

            reservationId.setText(String.valueOf(reservation.getId()));
            accId.setText(String.valueOf(reservation.getAccommodation().getId()));
            price.setText(String.valueOf(reservation.getTotalPrice()));

            Date strDate = reservation.getStartDate();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String formattedDate = sdf.format(strDate);
            startDate.setText(formattedDate);
            formattedDate = sdf.format(reservation.getEndDate());
            endDate.setText(formattedDate);

            if(isGuest){
                btnApproveReview.setVisibility(View.GONE);
                btnDeleteReview.setVisibility(View.GONE);

                cancelRes.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if(listener != null){
                            listener.onCancelClick(getAdapterPosition());
                        }
                    }
                });
            }else {
                cancelRes.setVisibility(View.GONE);

                btnApproveReview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            listener.onApproveClick(getAdapterPosition());
                        }
                    }
                });

                btnDeleteReview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            listener.onDeleteClick(getAdapterPosition());
                        }
                    }
                });
            }
        }
    }
}
