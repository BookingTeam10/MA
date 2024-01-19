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
import com.example.shopapp.model.review.ReviewOwner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OwnerRateReportAdapter extends RecyclerView.Adapter<OwnerRateReportAdapter.ViewHolder> {

    private List<ReviewOwner> reports;
    private OwnerRateReportAdapter.OnButtonClickListener listener;

    public interface OnButtonClickListener {
        void onDeleteClick(int position);
        void onApproveClick(int position);
    }

    public OwnerRateReportAdapter(List<ReviewOwner> reports, OwnerRateReportAdapter.OnButtonClickListener listener){
        this.reports = reports;
        this.listener = listener;
    }



    @NonNull
    @Override
    public OwnerRateReportAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_owner_rate_report, parent, false);
        return new OwnerRateReportAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OwnerRateReportAdapter.ViewHolder holder, int position) {
        ReviewOwner report = reports.get(position);
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
        private TextView commentDate;
        private TextView ownerId, guestId;
        private Button btnDeleteReview;
        private Button btnApproveReview;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            reviewId = itemView.findViewById(R.id.reviewId);
            rate = itemView.findViewById(R.id.rate);
            comment = itemView.findViewById(R.id.comment);
            commentDate = itemView.findViewById(R.id.commentDate);
            ownerId = itemView.findViewById(R.id.ownerId);
            guestId = itemView.findViewById(R.id.guestId);
            btnDeleteReview = itemView.findViewById(R.id.btnDeleteReview);
            btnApproveReview = itemView.findViewById(R.id.btnApproveReview);

        }


        public void bind(ReviewOwner report) {
            reviewId.setText(String.valueOf(report.getId()));
            rate.setText(String.valueOf(report.getRate()));
            comment.setText(String.valueOf(report.getComment()));
            Date date = report.getCommentDate();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String formattedDate = sdf.format(date);
            commentDate.setText(formattedDate);
            ownerId.setText(String.valueOf(report.getOwner().getId()));
            guestId.setText(String.valueOf(report.getGuest().getId()));


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
