package com.example.shopapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopapp.R;
import com.example.shopapp.model.accommodation.Accommodation;

import java.util.List;

public class AccommodationTableAdapter extends RecyclerView.Adapter<AccommodationTableAdapter.ViewHolder> {

    private List<Accommodation> accommodations;
    private OnButtonClickListener listener;


    public interface OnButtonClickListener {
        void onApproveClick(int position);
        void onRejectClick(int position);
    }


    public AccommodationTableAdapter(List<Accommodation> accommodations, AccommodationTableAdapter.OnButtonClickListener listener) {
        this.accommodations = accommodations;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_accommodation, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Accommodation accommodation = accommodations.get(position);
        holder.bind(accommodation);
    }

    @Override
    public int getItemCount() {
        return accommodations.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {

        private TextView roomId, ownerId, status, action;
        private Button btnApprove, btnReject;

        public ViewHolder(View itemView) {
            super(itemView);

            roomId = itemView.findViewById(R.id.roomId);
            ownerId = itemView.findViewById(R.id.ownerId);
            status = itemView.findViewById(R.id.statusId);
            btnApprove = itemView.findViewById(R.id.btnApprove);
            btnReject = itemView.findViewById(R.id.btnReject);
        }

        public void bind(Accommodation accommodation) {
            roomId.setText(String.valueOf(accommodation.getId()));
            ownerId.setText(String.valueOf(accommodation.getOwner().getName()));
            status.setText(String.valueOf(accommodation.getAccommodationStatus()));

            btnApprove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onApproveClick(getAdapterPosition());
                    }
                }
            });

            btnReject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onRejectClick(getAdapterPosition());
                    }
                }
            });
        }
    }
}
