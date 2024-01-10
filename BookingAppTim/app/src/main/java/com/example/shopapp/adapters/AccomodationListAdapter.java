package com.example.shopapp.adapters;



import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.example.shopapp.activities.GuestScreen.AccommodationDetailsScreen;
import com.example.shopapp.model.accommodation.Accommodation;

import com.example.shopapp.R;

import java.util.ArrayList;

public class AccomodationListAdapter extends ArrayAdapter<Accommodation> {
    private ArrayList<Accommodation> aAccomodation;

    public AccomodationListAdapter(Context context, FragmentManager supportFragmentManager, ArrayList<Accommodation> accomodations){
        super(context, R.layout.accomodation_card, accomodations);
        aAccomodation = accomodations;
    }
    @Override
    public int getCount() {
        return aAccomodation.size();
    }
    @Nullable
    @Override
    public Accommodation getItem(int position) {
        return aAccomodation.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Accommodation product = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.accomodation_card,
                    parent, false);
        }
        LinearLayout productCard = convertView.findViewById(R.id.product_card_item);
        ImageView imageView = convertView.findViewById(R.id.product_image);
        TextView productTitle = convertView.findViewById(R.id.product_title);
        TextView productDescription = convertView.findViewById(R.id.product_description);
        ImageButton imageButton = convertView.findViewById(R.id.button_star);

        if(product != null){
            imageView.setImageResource(product.getImage());
            productTitle.setText(product.getName());
            productDescription.setText(product.getDescription());
            productCard.setOnClickListener(v -> {
                Intent intent = new Intent(getContext(),AccommodationDetailsScreen.class);
                getContext().startActivity(intent);
            });

            imageButton.setOnClickListener(v -> {
                    v.setSelected(!v.isSelected());
            });
        }

        return convertView;
    }
}
