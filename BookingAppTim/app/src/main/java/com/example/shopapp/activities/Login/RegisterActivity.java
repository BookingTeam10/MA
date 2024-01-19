package com.example.shopapp.activities.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.shopapp.R;
import com.example.shopapp.activities.GuestScreen.GuestMainActivity;
import com.example.shopapp.configuration.ServiceUtils;
import com.example.shopapp.enums.TypeUser;
import com.example.shopapp.model.login.RegistrationDTO;
import com.example.shopapp.model.user.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button btnRegister= findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                EditText txtName = findViewById(R.id.txtName);
                String firstName = txtName.getText().toString();

                EditText txtLastName = findViewById(R.id.txtSurname);
                String lastName = txtLastName.getText().toString();

                EditText txtPhone = findViewById(R.id.etRegisterPhoneNumber);
                String phone = txtPhone.getText().toString();

                EditText txtEmail = findViewById(R.id.etRegisterEmail);
                String email = txtEmail.getText().toString();

                EditText txtAddress = findViewById(R.id.txtAddress);
                String address = txtAddress.getText().toString();

                EditText txtPassword = findViewById(R.id.etRegisterPassword);
                String password = txtPassword.getText().toString();

                EditText txtRepeatPassword = findViewById(R.id.confirmPassword);
                String repeatPassword = txtRepeatPassword.getText().toString();

                RadioGroup radioGroup = findViewById(R.id.userRadioGroup); // Zamenite sa stvarnim ID-om vašeg RadioGroup-a
                int selectedId = radioGroup.getCheckedRadioButtonId();
                String roleString = "";
                if (selectedId == R.id.ownerRadioButton) {
                    roleString = "OWNER";
                } else if (selectedId == R.id.guestRadioButton) {
                    roleString = "GUEST";
                }

                if(!password.equals(repeatPassword))
                {
                    Toast.makeText(RegisterActivity.this, "Passwords don't match!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(password.length() < 6)
                {
                    Toast.makeText(RegisterActivity.this, "Password must me longer then 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                RegistrationDTO registrationDTO = new RegistrationDTO();
                registrationDTO.setFirstName(firstName);
                registrationDTO.setLastName(lastName);
                registrationDTO.setAddress(address);
                registrationDTO.setPassword(password);
                registrationDTO.setEmail(email);
                registrationDTO.setPhoneNumber(phone);
                TypeUser role = TypeUser.valueOf(roleString);
                registrationDTO.setUserType(role);
                Call<User> call = ServiceUtils.unregisteredUserService.register(registrationDTO);
                call.enqueue(new Callback<User>() {
                                 @Override
                                 public void onResponse(Call<User> call, Response<User> response) {
                                     Toast.makeText(RegisterActivity.this, "Check your email!", Toast.LENGTH_SHORT).show();
                                 }

                                 @Override
                                 public void onFailure(Call<User> call, Throwable t) {
                                     //Toast.makeText(RegisterActivity.this, "Registration failed!", Toast.LENGTH_SHORT).show();
                                 }
                             }

                );
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("BookingAppTim", "RegisterActivity onStart()");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.d("BookingAppTim", "RegisterActivity onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("BookingAppTim", "RegisterActivity onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("BookingAppTim", "RegisterActivity onStop()");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("BookingAppTim", "RegisterActivity onDestroy()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("BookingAppTim", "RegisterActivity onRestart()");
    }


}