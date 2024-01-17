package com.example.shopapp.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopapp.R;
import com.example.shopapp.model.review.Review;
import com.example.shopapp.model.user.Guest;

import java.util.List;

public class CommentsListAdapter extends RecyclerView.Adapter<CommentsListAdapter.ViewHolder> {
    private List<Review> reviews;

    public CommentsListAdapter(List<Review> reviews) {
        this.reviews = reviews;
    }

    public static String getRole(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE);
        String role = sharedPreferences.getString("pref_role", "undefined");
        return role;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String comment = reviews.get(position).getComment();
        holder.commentTextView.setText(comment);
        Double rate = reviews.get(position).getRate();
        holder.textViewRating.setText(String.valueOf(rate));
        Guest guest = reviews.get(position).getReservation().getGuest();
        holder.textGuest.setText(guest.getName()+" " + guest.getSurname());
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView commentTextView,textViewRating,textGuest;

        Button buttonReport;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            String role = getRole(itemView.getContext());
            commentTextView = itemView.findViewById(R.id.textViewComment);
            textViewRating = itemView.findViewById(R.id.textViewRating);
            textGuest = itemView.findViewById(R.id.tvGuestName);
            buttonReport = itemView.findViewById(R.id.reportComment);
            if(!role.equals("Owner")){
                buttonReport .setVisibility(View.INVISIBLE);
            }
        }
    }

}
