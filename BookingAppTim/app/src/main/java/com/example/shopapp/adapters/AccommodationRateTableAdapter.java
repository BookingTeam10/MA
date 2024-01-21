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
import com.example.shopapp.model.review.Review;

import java.util.List;

public class AccommodationRateTableAdapter extends RecyclerView.Adapter<AccommodationRateTableAdapter.ViewHolder> {

    private List<Review> reports;
    private AccommodationRateTableAdapter.OnButtonClickListener listener;

    public interface OnButtonClickListener {
        void onDeleteClick(int position);
        void onApproveClick(int position);
    }

    public AccommodationRateTableAdapter(List<Review> reports, AccommodationRateTableAdapter.OnButtonClickListener listener){
        this.reports = reports;
        this.listener = listener;
    }



    @NonNull
    @Override
    public AccommodationRateTableAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_acc_rate_report, parent, false);
        return new AccommodationRateTableAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AccommodationRateTableAdapter.ViewHolder holder, int position) {
        Review report = reports.get(position);
        holder.bind(report);
    }

    @Override
    public int getItemCount() {
        return reports.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView reviewId;
        private TextView rate;
        private TextView comment;
        private TextView status;
        private TextView reservationId;
        private Button btnDeleteReview;
        private Button btnApproveReview;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            reviewId = itemView.findViewById(R.id.reviewId);
            rate = itemView.findViewById(R.id.rate);
            comment = itemView.findViewById(R.id.comment);
//            status = itemView.findViewById(R.id.status);
            reservationId = itemView.findViewById(R.id.reservationId);
            btnDeleteReview = itemView.findViewById(R.id.btnDeleteReview);
            btnApproveReview = itemView.findViewById(R.id.btnApproveReview);

        }


        public void bind(Review report) {
            reviewId.setText(String.valueOf(report.getId()));
            comment.setText(report.getComment());
            rate.setText(String.valueOf(report.getRate()));
//            status.setText(String.valueOf(report.getStatus()));
            reservationId.setText(String.valueOf(report.getReservation().getId()));

            if (report.getStatus() == ReviewStatus.WAITING) {
                btnDeleteReview.setVisibility(View.GONE);
            }

            btnApproveReview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        listener.onApproveClick(getAdapterPosition());
                    }
                }
            });

            btnDeleteReview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        listener.onDeleteClick(getAdapterPosition());
                    }
                }
            });
        }
    }


}
