package com.example.shopapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopapp.R;
import com.example.shopapp.model.review.UserReport;

import java.util.List;

public class UserReportTableAdapter extends RecyclerView.Adapter<UserReportTableAdapter.ViewHolder> {

    private List<UserReport> userReports;
    private UserReportTableAdapter.OnButtonClickListener listener;




    public interface OnButtonClickListener {
        void onDeleteClick(int position);
        void onBlockClick(int position);
    }

    public UserReportTableAdapter(List<UserReport> reports, UserReportTableAdapter.OnButtonClickListener listener){
        this.userReports = reports;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_report, parent, false);
        return new UserReportTableAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            UserReport userReport = userReports.get(position);
            holder.bind(userReport);
    }

    @Override
    public int getItemCount() {
        return userReports.size();
    }


    public  class ViewHolder extends RecyclerView.ViewHolder {

        private TextView reportId, reason, guestId, reportedUser, ownerId;
        private Button btnDelete, btnBlock;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            reportId = itemView.findViewById(R.id.reportId);
            reason = itemView.findViewById(R.id.reason);
            guestId = itemView.findViewById(R.id.guestId);
            ownerId = itemView.findViewById(R.id.ownerID);
            reportedUser = itemView.findViewById(R.id.reportedUser);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnBlock = itemView.findViewById(R.id.btnBlock);
        }

        public void bind(UserReport userReport){
            reportId.setText(String.valueOf(userReport.getId()));
            reason.setText(userReport.getComment());
            guestId.setText(String.valueOf(userReport.getGuest().getId()));
            ownerId.setText(String.valueOf(userReport.getOwner().getId()));
            if(userReport.getUserReportUser().equalsIgnoreCase("go")){
                reportedUser.setText("Owner");
            }else{
                reportedUser.setText("Guest");
            }

            btnBlock.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        listener.onBlockClick(getAdapterPosition());
                    }
                }
            });

            btnDelete.setOnClickListener(new View.OnClickListener() {
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
