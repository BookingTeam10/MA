package com.example.shopapp.fragments.profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.shopapp.R;
import com.example.shopapp.configuration.ServiceUtils;
import com.example.shopapp.dto.GuestDTO;
import com.example.shopapp.model.user.Administrator;
import com.example.shopapp.model.user.Guest;
import com.example.shopapp.model.user.Owner;
import com.example.shopapp.model.user.User;
import com.example.shopapp.services.interfaces.users.IAdminService;
import com.example.shopapp.services.interfaces.users.IGuestService;
import com.example.shopapp.services.interfaces.users.IOwnerService;

import org.w3c.dom.Text;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    private Guest guest;
    private Owner owner;
    private Administrator administrator;

    private String role = "";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view;

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("preferences", Context.MODE_PRIVATE);

        String role = sharedPreferences.getString("pref_role", "");
        String email = sharedPreferences.getString("pref_email", "");

        this.role = role;


        if(role.equals("Guest")) {
            view = inflater.inflate(R.layout.fragment_guest_profile_layout, container, false);

            View includedLayout = view.findViewById(R.id.nav_header);
            this.loadUser(view, includedLayout, email);


        }else if(role.equalsIgnoreCase("owner")){
            view = inflater.inflate(R.layout.fragment_owner_profile_layout, container, false);

            View includedLayout = view.findViewById(R.id.nav_header);
            this.loadUser(view, includedLayout, email);
        }else{
            view = inflater.inflate(R.layout.fragment_admin_profile_layout, container, false);

            View includedLayout = view.findViewById(R.id.nav_header);
            this.loadUser(view, includedLayout, email);
        }





        return view;
    }

    private void loadUser(View view, View includedLayout, String email) {


        if(role.equalsIgnoreCase("guest")) {
            IGuestService guestService = ServiceUtils.guestService;
            Call<Guest> call = guestService.getUserByUsername(email);

            call.enqueue(new Callback<Guest>() {
                @Override
                public void onResponse(Call<Guest> call, retrofit2.Response<Guest> response) {
                    if (response.isSuccessful()) {
                        guest = response.body();
                        updateGuestUI(view, includedLayout);

                    }
                }

                @Override
                public void onFailure(Call<Guest> call, Throwable t) {
                    Log.e("ProfileFragment", "Failed to retrieve user information: " + t.getMessage());
                }

            });
        }else if(role.equalsIgnoreCase("owner")){
            IOwnerService ownerService = ServiceUtils.ownerService;
            Call<Owner> call = ownerService.getOwnerByUsername(email);

            call.enqueue(new Callback<Owner>() {
                @Override
                public void onResponse(Call<Owner> call, Response<Owner> response) {
                    if(response.isSuccessful()){
                        owner = response.body();
                        updateOwnerUI(view, includedLayout);
                    }
                }

                @Override
                public void onFailure(Call<Owner> call, Throwable t) {

                }
            });
        }else{
            IAdminService ownerService = ServiceUtils.adminService;
            Call<Administrator> call = ownerService.getAdministratorByUsername(email);

            call.enqueue(new Callback<Administrator>() {
                @Override
                public void onResponse(Call<Administrator> call, Response<Administrator> response) {
                    if(response.isSuccessful()){
                        administrator = response.body();
                        updateAdminUI(view, includedLayout);
                    }
                }

                @Override
                public void onFailure(Call<Administrator> call, Throwable t) {

                }
            });
        }

    }

    private void updateAdminUI(View view, View includedLayout) {
        if(administrator != null){
            TextView emailTextView = view.findViewById(R.id.emailTextView);
            TextView nameTextView = view.findViewById(R.id.nameTextView);
            TextView surnameTextView = view.findViewById(R.id.surnameTextView);
            TextView user_name = includedLayout.findViewById(R.id.user_name);

            emailTextView.setText("Email: " + administrator.getEmail());
            nameTextView.setText("Name: " + administrator.getName());
            surnameTextView.setText("Surname: " + administrator.getSurname());
            user_name.setText(administrator.getName() + " " + administrator.getSurname());

            Button editProfileButton = view.findViewById(R.id.editProfileButton);

            editProfileButton.setOnClickListener(v -> {
                Bundle bundle = new Bundle();
                bundle.putParcelable("admin", (Parcelable) administrator);

                AdminEditProfileFragment adminEditProfileFragment = new AdminEditProfileFragment();
                adminEditProfileFragment.setArguments(bundle);

                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, adminEditProfileFragment)
                        .addToBackStack("adminProfileFragment")
                        .commit();
            });
        }
    }

    private void updateOwnerUI(View view, View includedLayout) {
        if (owner != null) {

            TextView emailTextView = view.findViewById(R.id.emailTextView);
            TextView nameTextView = view.findViewById(R.id.nameTextView);
            TextView surnameTextView = view.findViewById(R.id.surnameTextView);
            TextView phoneTextView = view.findViewById(R.id.phoneTextView);
            TextView addressTextView = view.findViewById(R.id.addressTextView);
            TextView user_name = includedLayout.findViewById(R.id.user_name);
            CheckBox ratedMe = view.findViewById(R.id.ratedMeNotificationCheckBox);
            CheckBox cancelled = view.findViewById(R.id.cancelledNotificationCheckBox);
            CheckBox created = view.findViewById(R.id.createdNotificationCheckBox);
            CheckBox rateAccommodation = view.findViewById(R.id.rateAccommodationNotificationCheckBox);



            emailTextView.setText("Email: " + owner.getEmail());
            nameTextView.setText("Name: " + owner.getName());
            surnameTextView.setText("Surname: " + owner.getSurname());
            phoneTextView.setText("Phone: " + owner.getPhone());
            addressTextView.setText("Address: " + owner.getAddress());
            user_name.setText(owner.getName() + " " + owner.getSurname());
            created.setChecked(owner.isCreatedNotification());
            ratedMe.setChecked(owner.isRateMeNotification());
            cancelled.setChecked(owner.isCancelledNotification());
             rateAccommodation.setChecked(owner.isRateAccommodationNotification());

            Button editProfileButton = view.findViewById(R.id.editProfileButton);

            editProfileButton.setOnClickListener(v -> {
                Bundle bundle = new Bundle();
                bundle.putParcelable("owner", (Parcelable) owner);

                OwnerEditProfileFragment ownerEditProfileFragment = new OwnerEditProfileFragment();
                ownerEditProfileFragment.setArguments(bundle);

                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, ownerEditProfileFragment)
                        .addToBackStack("ownerProfileFragment")
                        .commit();
            });
        }
    }


    private void updateGuestUI(View view, View includedLayout) {
        if (guest != null) {

            TextView emailTextView = view.findViewById(R.id.emailTextView);
            TextView nameTextView = view.findViewById(R.id.nameTextView);
            TextView surnameTextView = view.findViewById(R.id.surnameTextView);
            TextView phoneTextView = view.findViewById(R.id.phoneTextView);
            TextView addressTextView = view.findViewById(R.id.addressTextView);
            TextView user_name = includedLayout.findViewById(R.id.user_name);
            CheckBox checkBox = view.findViewById(R.id.notificationsCheckBox);

            emailTextView.setText("Email: " + guest.getEmail());
            nameTextView.setText("Name: " + guest.getName());
            surnameTextView.setText("Surname: " + guest.getSurname());
            phoneTextView.setText("Phone: " + guest.getPhone());
            addressTextView.setText("Address: " + guest.getAddress());
            user_name.setText(guest.getName() + " " + guest.getSurname());
            checkBox.setChecked(guest.isTurnOnNotification());

            Button editProfileButton = view.findViewById(R.id.editProfileButton);

            editProfileButton.setOnClickListener(v -> {
                Bundle bundle = new Bundle();
                bundle.putParcelable("guest", (Parcelable) guest);

                GuestEditProfileFragment editProfileFragment = new GuestEditProfileFragment();
                editProfileFragment.setArguments(bundle);

                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, editProfileFragment)
                        .addToBackStack("profileFragment")
                        .commit();
            });
        }

    }

    @Override
    public void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("preferences", Context.MODE_PRIVATE);

        String email = sharedPreferences.getString("pref_email", "");

        View view = getView();
        View includedLayout = view != null ? view.findViewById(R.id.nav_header) : null;
        loadUser(view, includedLayout, email);

    }
}