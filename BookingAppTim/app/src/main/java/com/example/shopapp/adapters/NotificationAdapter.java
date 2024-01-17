package com.example.shopapp.adapters;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopapp.R;
import com.example.shopapp.enums.NotificationStatus;
import com.example.shopapp.model.notifications.NotificationUserVisible;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    private List<NotificationUserVisible> notifications;

    public NotificationAdapter(List<NotificationUserVisible> notifications) {
        this.notifications = notifications;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NotificationUserVisible notification = notifications.get(position);
        holder.textViewNotification.setText(notification.getText());
        if (notification.getStatus() == NotificationStatus.NOT_VISIBLE) {
            holder.textViewNotification.setTypeface(null, Typeface.BOLD);
            holder.textViewContact.setTypeface(null, Typeface.BOLD);
            holder.itemView.setBackgroundColor(Color.parseColor("#CCCCCC"));
        } else {
            holder.textViewNotification.setTypeface(null, Typeface.NORMAL);
            holder.textViewContact.setTypeface(null, Typeface.NORMAL);
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }
    }
    @Override
    public int getItemCount() {
        return notifications.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNotification,textViewContact,textViewTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNotification = itemView.findViewById(R.id.notification_text);
            textViewContact = itemView.findViewById(R.id.contact_name);
            textViewTime = itemView.findViewById(R.id.time_and_date);
        }
    }
}