package com.example.shopapp.fragments.profile;

import static com.example.shopapp.configuration.ServiceUtils.adminService;
import static com.example.shopapp.configuration.ServiceUtils.guestService;

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
import com.example.shopapp.dto.AdministratorDTO;
import com.example.shopapp.dto.GuestDTO;
import com.example.shopapp.model.user.Administrator;
import com.example.shopapp.model.user.Guest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminEditProfileFragment extends Fragment {
    private Administrator admin;
    private TextView textView;
    private EditText emailEditText, nameEditText, surnameEditText;
    private Button saveProfileButton, cancelButton, changePasswordButton, deleteButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_edit_profile, container, false);

        Bundle bundle = getArguments();
        if (bundle != null) {
            admin = bundle.getParcelable("admin");
        }

        textView = view.findViewById(R.id.user_name);
        textView.setText(admin.getName() + " " +  admin.getSurname());
        emailEditText = view.findViewById(R.id.emailEditText);
        emailEditText.setText(admin.getEmail());
        nameEditText = view.findViewById(R.id.nameEditText);
        nameEditText.setText(admin.getName());
        surnameEditText = view.findViewById(R.id.surnameEditText);
        surnameEditText.setText(admin.getSurname());

        saveProfileButton = view.findViewById(R.id.saveButton);
        saveProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String name = nameEditText.getText().toString().trim();
                String surname = surnameEditText.getText().toString().trim();


                if (email.isEmpty() || name.isEmpty() || surname.isEmpty()) {
                    Toast.makeText(requireContext(), "Please fill in all required fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                Administrator updatedAdmin = new Administrator();
                updatedAdmin.setId(admin.getId());
                updatedAdmin.setEmail(email);
                updatedAdmin.setName(name);
                updatedAdmin.setSurname(surname);
                updatedAdmin.setPassword(admin.getPassword());

                checkAdmin(updatedAdmin);

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
                bundle.putParcelable("admin", (Parcelable) admin);
                bundle.putString("role", "admin");

                changePasswordFragment.setArguments(bundle);

                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, changePasswordFragment)
                        .addToBackStack("admineditprofile")
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
                        deleteAdmin(admin);

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

    private void checkAdmin(Administrator updatedAdmin) {

        if (!this.admin.getEmail().equals(updatedAdmin.getEmail())) {
            Log.e("guest", this.admin.getEmail() + "    ++==  " + updatedAdmin.getEmail());
            Call<Boolean> callUsername = guestService.checkUsername(updatedAdmin.getEmail());

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
                            updateAdmin(updatedAdmin, true);
                        }
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {

                }
            });
        } else {
            updateAdmin(updatedAdmin, false);
        }
    }

        private void updateAdmin (Administrator updatedAdmin,boolean logOut){
            Call<AdministratorDTO> call2 = adminService.updateAdminProfile(updatedAdmin.getId(), updatedAdmin);

            call2.enqueue(new Callback<AdministratorDTO>() {
                @Override
                public void onResponse(Call<AdministratorDTO> call, Response<AdministratorDTO> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(requireContext(), "Account changes successful!", Toast.LENGTH_SHORT).show();
                        if (logOut) {
                            Intent intent = new Intent(requireContext(), LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            requireActivity().finish();
                        } else {
                            Log.e("CANCELBUTTON", "USLO CANCEL");
                            cancelButton.performClick();
                        }
                    }
                }

                @Override
                public void onFailure(Call<AdministratorDTO> call, Throwable t) {
                    Log.e("UpdateGuestFailure", t.getMessage());
                }
            });

        }

    private void deleteAdmin(Administrator admin) {
        Call call = adminService.deleteAdmin(admin.getId());

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
