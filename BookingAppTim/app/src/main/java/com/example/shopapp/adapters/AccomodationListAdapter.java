package com.example.shopapp.adapters;



import static com.example.shopapp.fragments.accomodations.AccomodationsPageFragment.accommodations;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.example.shopapp.activities.GuestScreen.AccommodationDetailsScreen;
import com.example.shopapp.configuration.ServiceUtils;
import com.example.shopapp.dto.GuestDTO;
import com.example.shopapp.model.accommodation.Accommodation;

import com.example.shopapp.R;
import com.example.shopapp.model.user.Guest;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    public void setAccommodations(ArrayList<Accommodation> newAccommodations) {
        this.aAccomodation = newAccommodations;
        notifyDataSetChanged(); // Ovo obaveštava adapter da se podaci promenili
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Accommodation accommodation = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.accomodation_card,
                    parent, false);
        }
        LinearLayout productCard = convertView.findViewById(R.id.product_card_item);
        ImageView imageView = convertView.findViewById(R.id.product_image);
        TextView productTitle = convertView.findViewById(R.id.product_title);
        TextView productDescription = convertView.findViewById(R.id.product_description);
        ImageButton imageButton = convertView.findViewById(R.id.button_star);

        if(accommodation != null){
            imageView.setImageResource(accommodation.getImage());
            productTitle.setText(accommodation.getName());
            productDescription.setText(accommodation.getDescription());
            productCard.setOnClickListener(v -> {
                Intent intent = new Intent(getContext(),AccommodationDetailsScreen.class);
                intent.putExtra("accommodation", accommodation);
                getContext().startActivity(intent);
            });

            imageButton.setOnClickListener(v -> {
                v.setSelected(!v.isSelected());
                addFavouriteAccommodation(accommodation,v.isSelected());

            });
        }

        return convertView;
    }

    //pokusati izbaciti iz adaptera i treba azurirati accommodation
    private void addFavouriteAccommodation(Accommodation accommodation, boolean isSelected) {
        //add favourite
        if (isSelected) {
            Log.i("DODATI","DODATI");
            Call<GuestDTO> call = ServiceUtils.guestService.addFavouriteAccommodation(3L,accommodation);

            call.enqueue(new Callback<GuestDTO>() {
                @Override
                public void onResponse(Call<GuestDTO> call, Response<GuestDTO> response) {
                    if (response.code() == 204){
                        Log.d("REZ","Meesage recieved");
                        System.out.println(response.body());
                        Log.i("AZURIRANO",response.body().toString());
                        //treba gosta azurirati
                    }
                }
                @Override
                public void onFailure(Call<GuestDTO> call, Throwable t) {
                    Log.d("REZ123", t.getMessage() != null?t.getMessage():"error");
                }
            });

        } else {
            //remove favourite
            Log.i("UKLONITI","UKLONITI");
            Call<GuestDTO> call = ServiceUtils.guestService.deleteFavouriteAccommodation(3L,accommodation.getId());
            call.enqueue(new Callback<GuestDTO>() {
                @Override
                public void onResponse(Call<GuestDTO> call, Response<GuestDTO> response) {
                    Log.i("UDJE DELETE","UDJE DELETE");
                    if(!response.isSuccessful()){
                        //Toast.makeText(getActivity(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                }
                @Override
                public void onFailure(Call<GuestDTO> call, Throwable t) {
                    Log.d("Delete fail", "Coudlnt delete favourite accommodation");
                }
            });
        }
    }
}
