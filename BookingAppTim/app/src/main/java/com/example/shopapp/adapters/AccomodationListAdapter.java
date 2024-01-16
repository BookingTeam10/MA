package com.example.shopapp.adapters;



import static com.google.maps.android.Context.getApplicationContext;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.example.shopapp.activities.GuestScreen.AccommodationDetailsScreen;
import com.example.shopapp.activities.HostScreen.reports.AccommodationReportActivity;
import com.example.shopapp.activities.HostScreen.DefinitionAccommodationActivity;
import com.example.shopapp.configuration.ServiceUtils;
import com.example.shopapp.dto.AccommodationDTO;
import com.example.shopapp.dto.GuestDTO;
import com.example.shopapp.fragments.guest.favourite_accomodations.FavouriteAccommodationPageViewModel;
import com.example.shopapp.fragments.guest.favourite_accomodations.FavouriteAccommodationsListFragment;
import com.example.shopapp.model.accommodation.Accommodation;

import com.example.shopapp.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccomodationListAdapter extends ArrayAdapter<Accommodation> {
    private ArrayList<Accommodation> aAccomodation;
    private FragmentManager fragmentManager;
    private FavouriteAccommodationPageViewModel productsViewModel;
    private ArrayList<AccommodationDTO> favouriteAccommodations;


    public AccomodationListAdapter(Context context, FragmentManager supportFragmentManager, ArrayList<Accommodation> accomodations){
        super(context, R.layout.accomodation_card, accomodations);
        aAccomodation = accomodations;
        fragmentManager=supportFragmentManager;
    }

    public String getRole(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE);
        String role = sharedPreferences.getString("pref_role", "undefined");
        return role;
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
        String role = getRole(getContext());
        Accommodation accommodation = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.accomodation_card,
                    parent, false);
        }
        LinearLayout productCard = convertView.findViewById(R.id.product_card_item);
        ImageView imageView = convertView.findViewById(R.id.product_image);
        TextView productTitle = convertView.findViewById(R.id.product_title);
        TextView productDescription = convertView.findViewById(R.id.product_description);
        Button reportAccommodation = convertView.findViewById(R.id.button_document_accommodation);
        Button editAccommodation = convertView.findViewById(R.id.button_edit);
        ImageButton imageButton = convertView.findViewById(R.id.button_star);

        Button editButton=convertView.findViewById(R.id.button_edit);

        getFavouriteAccommodations();

        if(accommodation != null){
            List<String> photos = accommodation.getPhotos();

            String firstPhoto = accommodation.getPhotos().get(0);

            productTitle.setText(accommodation.getName());
            productDescription.setText(accommodation.getDescription());
            productCard.setOnClickListener(v -> {
                Intent intent = new Intent(getContext(),AccommodationDetailsScreen.class);
                intent.putExtra("accommodation", accommodation);
                getContext().startActivity(intent);
            });

            if (!role.equals("Owner")){
                reportAccommodation.setVisibility(View.INVISIBLE);
            }
            if (!role.equals("Owner")){
                editAccommodation.setVisibility(View.INVISIBLE);
            }

            if (!role.equals("Guest")){
                imageButton.setVisibility(View.INVISIBLE);
            }

            imageButton.setOnClickListener(v -> {

                v.setSelected(!v.isSelected());
                addFavouriteAccommodation(accommodation,v.isSelected());

            });

            if (!role.equals("Owner")){
                editButton.setVisibility(View.INVISIBLE);
            }

            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), DefinitionAccommodationActivity.class);
                    intent.putExtra("accommodation", accommodation);
                    getContext().startActivity(intent);
                }
            });

            reportAccommodation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), AccommodationReportActivity.class);
                    intent.putExtra("accommodation", accommodation);
                    getContext().startActivity(intent);

                }
            });
        }

        return convertView;
    }

    private void addFavouriteAccommodation(Accommodation accommodation, boolean isSelected) {
        //add favourite
        if (isSelected) {
            Call<GuestDTO> call = ServiceUtils.guestService.addFavouriteAccommodation(3L,accommodation.getId());
            call.enqueue(new Callback<GuestDTO>() {
                @Override
                public void onResponse(Call<GuestDTO> call, Response<GuestDTO> response) {
                    if (response.code() == 204){
                        System.out.println(response.body());
                        Log.i("AZURIRANO",response.body().toString());
                        //treba gosta azurirati
                        GuestDTO guestDTO = response.body();
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
                    GuestDTO guestDTO = response.body();
                }
                @Override
                public void onFailure(Call<GuestDTO> call, Throwable t) {
                    Log.d("Delete fail", "Coudlnt delete favourite accommodation");
                }
            });
        }
    }

    private void getFavouriteAccommodations() {
            favouriteAccommodations = new ArrayList<>();
            Call<ArrayList<AccommodationDTO>> call = ServiceUtils.guestService.getFavouriteAccommodation(3L);
            call.enqueue(new Callback<ArrayList<AccommodationDTO>>() {
                @Override
                public void onResponse(Call<ArrayList<AccommodationDTO>> call, Response<ArrayList<AccommodationDTO>> response) {
                    if (response.code() == 200){
                        System.out.println(response.body());
                        Log.i("AZURIRANO",response.body().toString());
                        favouriteAccommodations = response.body();
                        Log.i("FAVOURITE GET",favouriteAccommodations.toString());
                    }
                }
                @Override
                public void onFailure(Call<ArrayList<AccommodationDTO>> call, Throwable t) {
                    Log.d("REZ123", t.getMessage() != null?t.getMessage():"error");
                }
            });
    }

}
