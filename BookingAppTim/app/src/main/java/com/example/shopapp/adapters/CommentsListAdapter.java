package com.example.shopapp.adapters;

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

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopapp.R;
import com.example.shopapp.configuration.ServiceUtils;
import com.example.shopapp.enums.NotificationStatus;
import com.example.shopapp.enums.ReviewStatus;
import com.example.shopapp.fragments.guest.reservations.myReservations.ReservationDetailFragment;
import com.example.shopapp.model.notifications.NotificationUserVisible;
import com.example.shopapp.model.reservation.Reservation;
import com.example.shopapp.model.review.Review;
import com.example.shopapp.model.review.ReviewOwner;
import com.example.shopapp.model.user.Guest;
import com.example.shopapp.model.user.Owner;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        Review review = reviews.get(position);

        holder.buttonReport.setOnClickListener(view -> {
            int adapterPosition = holder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                Review singleReview=reviews.get(adapterPosition);

                Log.d("SINGLE", singleReview.toString());

                Log.d("SINGLE1", singleReview.getStatus().toString());

                if(singleReview.getStatus().equals(ReviewStatus.REPORTED)){
                    Log.d("SINGLE2", "UDJE U REPORT");
                    new AlertDialog.Builder(view.getContext())
                            .setTitle("Review Owner Unavailable")
                            .setMessage("This is already reported by you!")
                            .setPositiveButton(android.R.string.ok, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }else{

                    Call<Review> call = ServiceUtils.reviewService.getReportComment(singleReview.getId());

                    call.enqueue(new Callback<Review>() {
                        @Override
                        public void onResponse(Call<Review> call, Response<Review> response) {
                            Log.d("RESEPONSE CODE DUGME MATIJA", String.valueOf(response.code()));
                            if (response.code() == 200){
                                Log.d("REZ","Meesage recieved");
                                System.out.println(response.body());
                                Review product1 = response.body();
                                System.out.println(product1);
                            }else{
                                Log.d("REZ","Meesage recieved: "+response.code());
                            }
                        }

                        @Override
                        public void onFailure(Call<Review> call, Throwable t) {
                            Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
                        }
                    });
                }
            }
        });
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
