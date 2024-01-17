package com.example.shopapp.fragments.profile;

import static com.example.shopapp.configuration.ServiceUtils.guestService;
import static com.example.shopapp.configuration.ServiceUtils.ownerService;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.shopapp.dto.OwnerDTO;
import com.example.shopapp.model.user.Guest;
import com.example.shopapp.model.user.Owner;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OwnerEditProfileFragment extends Fragment {


    private Owner owner;
    private TextView textView;
    private EditText emailEditText, nameEditText, surnameEditText, phoneEditText, addressEditText;
    private Button saveProfileButton, cancelButton, changePasswordButton, deleteButton;
    private CheckBox cancelled, ratedMe, rateAccommodation, created ;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_owner_edit_profile, container, false);

        Bundle bundle = getArguments();
        if (bundle != null) {
            owner = bundle.getParcelable("owner");
        }


        textView = view.findViewById(R.id.user_name);
        textView.setText(owner.getName() + " " + owner.getSurname());
        emailEditText = view.findViewById(R.id.emailEditText);
        emailEditText.setText(owner.getEmail());
        nameEditText = view.findViewById(R.id.nameEditText);
        nameEditText.setText(owner.getName());
        surnameEditText = view.findViewById(R.id.surnameEditText);
        surnameEditText.setText(owner.getSurname());
        phoneEditText = view.findViewById(R.id.phoneEditText);
        phoneEditText.setText(owner.getPhone());
        addressEditText = view.findViewById(R.id.addressEditText);
        addressEditText.setText(owner.getAddress());
        ratedMe = view.findViewById(R.id.ratedMeNotificationCheckBox);
        ratedMe.setChecked(owner.isRateMeNotification());
        cancelled = view.findViewById(R.id.cancelledNotificationCheckBox);
        cancelled.setChecked(owner.isCancelledNotification());
        created = view.findViewById(R.id.createdNotificationCheckBox);
        created.setChecked(owner.isCreatedNotification());
        rateAccommodation = view.findViewById(R.id.rateAccommodationNotificationCheckBox);
        rateAccommodation.setChecked(owner.isRateAccommodationNotification());


        saveProfileButton = view.findViewById(R.id.saveButton);
        saveProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String name = nameEditText.getText().toString().trim();
                String surname = surnameEditText.getText().toString().trim();
                String phone = phoneEditText.getText().toString().trim();
                String address = addressEditText.getText().toString().trim();
                boolean cancelledChecked = cancelled.isChecked();
                boolean createdChecked = created.isChecked();
                boolean rateMeChecked = created.isChecked();
                boolean rateAccChecked = rateAccommodation.isChecked();


                if (email.isEmpty() || name.isEmpty() || surname.isEmpty()) {
                    Toast.makeText(requireContext(), "Please fill in all required fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                Owner updatedOwner = new Owner();
                updatedOwner.setId(owner.getId());
                updatedOwner.setEmail(email);
                updatedOwner.setName(name);
                updatedOwner.setSurname(surname);
                updatedOwner.setPhone(phone);
                updatedOwner.setAddress(address);
                updatedOwner.setCreatedNotification(createdChecked);
                updatedOwner.setCancelledNotification(cancelledChecked);
                updatedOwner.setRateAccommodationNotification(rateAccChecked);
                updatedOwner.setRateMeNotification(rateMeChecked);
                updatedOwner.setPassword(owner.getPassword());

                checkOwner(updatedOwner);
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
                bundle.putParcelable("owner", (Parcelable) owner);
                bundle.putString("role", "owner");

                changePasswordFragment.setArguments(bundle);

                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, changePasswordFragment)
                        .addToBackStack("ownerteditprofile")
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
                        deleteOwner(owner);

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



    private void checkOwner(Owner updatedOwner) {

        if (!this.owner.getEmail().equals(updatedOwner.getEmail())) {
            Log.e("guest", this.owner.getEmail() + "    ++==  " + updatedOwner.getEmail());
            Call<Boolean> callUsername = guestService.checkUsername(updatedOwner.getEmail());

            callUsername.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, retrofit2.Response<Boolean> response) {
                    if (response.isSuccessful()) {
                        boolean exists = response.body();

                        if (exists) {
                            Toast.makeText(requireContext(), "Username already exists!", Toast.LENGTH_SHORT).show();
                            Log.e("USERNAME", String.valueOf(exists) + "  DAL POSTOJI");
                            return;
                        } else {
                            updateOwner(updatedOwner, true);
                        }
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {

                }
            });
        } else {
            updateOwner(updatedOwner, false);
        }

    }


        private void updateOwner(Owner updatedOwner, boolean logOut){
            Call<OwnerDTO> call2 = ownerService.updateOwnerProfile(updatedOwner.getId(),updatedOwner);

            call2.enqueue(new Callback<OwnerDTO>() {
                @Override
                public void onResponse(Call<OwnerDTO> call, Response<OwnerDTO> response) {
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
                public void onFailure(Call<OwnerDTO> call, Throwable t) {
                    Log.e("UpdateGuestFailure", t.getMessage());
                }
            });

        }

    private void deleteOwner(Owner owner) {
        Call call = ownerService.deleteOwner(owner.getId());

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
