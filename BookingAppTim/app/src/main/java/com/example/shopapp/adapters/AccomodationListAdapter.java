package com.example.shopapp.adapters;



import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.shopapp.model.Accomodation;

import com.example.shopapp.R;

import java.util.ArrayList;

public class AccomodationListAdapter extends ArrayAdapter<Accomodation> {
    private ArrayList<Accomodation> aAccomodation;

    public AccomodationListAdapter(Context context, ArrayList<Accomodation> accomodations){
        super(context, R.layout.accomodation_card, accomodations);
        aAccomodation = accomodations;
    }

    @Override
    public int getCount() {
        return aAccomodation.size();
    }

    @Nullable
    @Override
    public Accomodation getItem(int position) {
        return aAccomodation.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Accomodation product = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.accomodation_card,
                    parent, false);
        }
        LinearLayout productCard = convertView.findViewById(R.id.product_card_item);
        ImageView imageView = convertView.findViewById(R.id.product_image);
        TextView productTitle = convertView.findViewById(R.id.product_title);
        TextView productDescription = convertView.findViewById(R.id.product_description);

        if(product != null){
            imageView.setImageResource(product.getImage());
            productTitle.setText(product.getTitle());
            productDescription.setText(product.getDescription());
            productCard.setOnClickListener(v -> {
                // Handle click on the item at 'position'
                Log.i("ShopApp", "Clicked: " + product.getTitle() + ", id: " +
                        product.getId().toString());
                Toast.makeText(getContext(), "Clicked: " + product.getTitle()  +
                        ", id: " + product.getId().toString(), Toast.LENGTH_SHORT).show();
            });
        }

        return convertView;
    }
}
