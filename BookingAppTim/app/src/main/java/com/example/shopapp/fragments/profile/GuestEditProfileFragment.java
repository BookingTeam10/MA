package com.example.shopapp.fragments.profile;

import static com.example.shopapp.configuration.ServiceUtils.guestService;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.shopapp.R;
import com.example.shopapp.activities.Login.LoginActivity;
import com.example.shopapp.dto.GuestDTO;
import com.example.shopapp.model.user.Guest;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GuestEditProfileFragment extends Fragment {

    private Guest guest;
    private TextView textView;
    private EditText emailEditText, nameEditText, surnameEditText, phoneEditText, addressEditText;
    private Button saveProfileButton, cancelButton, changePasswordButton, deleteButton;
    private CheckBox turnOnNotifications;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guest_edit_profile, container, false);

        Bundle bundle = getArguments();
        if (bundle != null) {
            guest = bundle.getParcelable("guest");
        }

        textView = view.findViewById(R.id.user_name);
        textView.setText(guest.getName() + " " +  guest.getSurname());
        emailEditText = view.findViewById(R.id.emailEditText);
        emailEditText.setText(guest.getEmail());
        nameEditText = view.findViewById(R.id.nameEditText);
        nameEditText.setText(guest.getName());
        surnameEditText = view.findViewById(R.id.surnameEditText);
        surnameEditText.setText(guest.getSurname());
        phoneEditText = view.findViewById(R.id.phoneEditText);
        phoneEditText.setText(guest.getPhone());
        addressEditText = view.findViewById(R.id.addressEditText);
        addressEditText.setText(guest.getAddress());
        turnOnNotifications = view.findViewById(R.id.turnNotificationCheckBox);
        turnOnNotifications.setChecked(guest.isTurnOnNotification());

        saveProfileButton = view.findViewById(R.id.saveButton);
        saveProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String name = nameEditText.getText().toString().trim();
                String surname = surnameEditText.getText().toString().trim();
                String phone = phoneEditText.getText().toString().trim();
                String address = addressEditText.getText().toString().trim();
                boolean turnOnNotification = turnOnNotifications.isChecked();

                if (email.isEmpty() || name.isEmpty() || surname.isEmpty()) {
                    Toast.makeText(requireContext(), "Please fill in all required fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                Guest updatedGuest = new Guest();
                updatedGuest.setId(guest.getId());
                updatedGuest.setEmail(email);
                updatedGuest.setName(name);
                updatedGuest.setSurname(surname);
                updatedGuest.setPhone(phone);
                updatedGuest.setAddress(address);
                updatedGuest.setTurnOnNotification(turnOnNotification);
                updatedGuest.setPassword(guest.getPassword());

                checkGuest(updatedGuest);

            }
        });


        cancelButton = view.findViewById(R.id.cancelButton);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().popBackStack();
            }
        });

        changePasswordButton = view.findViewById(R.id.changePasswordButton);
        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangePasswordFragment changePasswordFragment = new ChangePasswordFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable("guest", (Parcelable) guest);
                bundle.putString("role", "guest");

                changePasswordFragment.setArguments(bundle);

                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, changePasswordFragment)
                        .addToBackStack("guesteditprofile")
                        .commit();
            }
        });

        deleteButton = view.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setTitle("Delete Confirmation");
                builder.setMessage("Are you sure you want to delete?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteGuest(guest);

                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });




        return view;
    }

    private void checkGuest(Guest updatedGuest) {

        if(!this.guest.getEmail().equals(updatedGuest.getEmail())){
            Log.e("guest", this.guest.getEmail() + "    ++==  " + updatedGuest.getEmail());
            Call<Boolean> callUsername = guestService.checkUsername(updatedGuest.getEmail());

            callUsername.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, retrofit2.Response<Boolean> response) {
                    if(response.isSuccessful()){
                        boolean exists = response.body();

                        if(exists){
                            Toast.makeText(requireContext(), "Username already exists!", Toast.LENGTH_SHORT).show();
                            Log.e("USERNAME", String.valueOf(exists) + "  DAL POSTOJI");
                            return;
                        }else{
                            updateGuest(updatedGuest, true);
                        }
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {

                }
            });
        }else{
            updateGuest(updatedGuest, false);
        }
    }

    private void updateGuest(Guest updatedGuest, boolean logOut){
        Call<GuestDTO> call2 = guestService.updateGuestProfile(updatedGuest.getId(),updatedGuest);

        call2.enqueue(new Callback<GuestDTO>() {
            @Override
            public void onResponse(Call<GuestDTO> call, Response<GuestDTO> response) {
                if (response.isSuccessful()) {
                        Toast.makeText(requireContext(), "Account changes successful!", Toast.LENGTH_SHORT).show();
                        if(logOut) {
                            Intent intent = new Intent(requireContext(), LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            requireActivity().finish();
                        }else{
                            Log.e("CANCELBUTTON", "USLO CANCEL");
                            cancelButton.performClick();
                        }
                }
            }

            @Override
            public void onFailure(Call<GuestDTO> call, Throwable t) {
                Log.e("UpdateGuestFailure", t.getMessage());
            }
        });

    }
    private void deleteGuest(Guest guest) {
        Call call = guestService.deleteGuest(guest.getId());

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, retrofit2.Response response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Deletion successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(requireContext(), LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    requireActivity().finish();
                }else{
                    if (response.code() == 403) {
                        Log.e("DeleteGuest", "Deletion forbidden: " + response.message());
                        Toast.makeText(getContext(), "Deletion not allowed", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e("DeleteGuest", "Deletion failed. Code: " + response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }

        });
    }
}
