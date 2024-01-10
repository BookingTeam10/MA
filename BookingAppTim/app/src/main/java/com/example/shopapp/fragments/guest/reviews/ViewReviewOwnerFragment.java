package com.example.shopapp.fragments.guest.reviews;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.example.shopapp.R;
import com.example.shopapp.model.review.ReviewOwner;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewReviewOwnerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewReviewOwnerFragment extends Fragment {

    ReviewOwner reviewOwner;
    public static ViewReviewOwnerFragment newInstance(String param1, String param2) {
        ViewReviewOwnerFragment fragment = new ViewReviewOwnerFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            reviewOwner = (ReviewOwner) getArguments().getSerializable("reviewOwner");
            Log.d("UCITA SE LEPO",reviewOwner.toString());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_review_owner, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView reviewDetails = view.findViewById(R.id.reviewDetails);

        String describe="";

        String date="";

        if(reviewOwner.getCommentDate()==null){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String formattedDate = sdf.format(new Date());
            date=formattedDate;
        }else{
            String[] parts = reviewOwner.getCommentDate().toString().split(" ");
            date = parts[0];
        }


        describe=describe+"Comment: "+reviewOwner.getComment()+"\n"+ "My rate owner: "+reviewOwner.getRate()+"\n"+"Date: "+date+"\n"+"Owner rate: "+reviewOwner.getOwner().getRating();


        reviewDetails.setText(describe);

    }
}