package com.example.shopapp.fragments.profile;

import static com.example.shopapp.configuration.ServiceUtils.userService;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.shopapp.R;
import com.example.shopapp.activities.Login.LoginActivity;
import com.example.shopapp.dto.PasswordDto;
import com.example.shopapp.model.user.Administrator;
import com.example.shopapp.model.user.Guest;
import com.example.shopapp.model.user.Owner;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordFragment extends Fragment {

    private String role;
    private Guest guest;
    private Owner owner;
    private Administrator admin;
    private Button cancelButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);


        Bundle bundle = getArguments();
        if (bundle != null) {
            role = bundle.getString("role");
            if(role.equals("guest")){
                guest = bundle.getParcelable("guest");
            }else if(role.equals("owner")){
                owner = bundle.getParcelable("owner");
            }else{
                admin = bundle.getParcelable("admin");
            }
        }


        EditText passwordEditText = view.findViewById(R.id.passwordEditText);
        EditText password2EditText = view.findViewById(R.id.password2EditText);
        cancelButton = view.findViewById(R.id.cancelButton);
        Button changePasswordButton = view.findViewById(R.id.changePasswordButton);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAdded()) {
                    requireActivity().getSupportFragmentManager().popBackStack();
                }
            }
        });

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String passwordInput = passwordEditText.getText().toString();
                String password2Input = password2EditText.getText().toString();

                if (!passwordInput.isEmpty() && !password2Input.isEmpty()) {
                    if (passwordInput.equals(password2Input)) {
                        if (admin != null) {
//                            userService.changePassword(passwordInput, admin.getId());
                        } else if (guest != null && guest.getId() != null) {
                            changePassword(passwordInput, guest.getId());
                        } else if (owner != null) {
                            changePassword(passwordInput,owner.getId());
                        } else {
                            // Handle the case when none of the conditions match
                        }

                        getParentFragmentManager().popBackStack();
                    } else {
                        wrongInput();
                    }
                } else {
                    wrongInput();
                }
            }
        });

        return view;
    }


        public void changePassword(String passwordInput, Long id) {
            PasswordDto passwordDto = new PasswordDto(passwordInput);

            Call<Void> call = userService.changePassword(id, passwordDto);

            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        cancelButton.performClick();
                    } else {

                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });
        }


    private void wrongInput() {
    }


}
