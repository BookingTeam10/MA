package com.example.shopapp.activities.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.shopapp.R;
import com.example.shopapp.activities.GuestScreen.GuestMainActivity;
import com.example.shopapp.activities.HostScreen.HostMainActivity;
import com.example.shopapp.databinding.ActivityLoginBinding;


public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button btnLogin= findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                EditText etUsername = findViewById(R.id.etUsername);
                String username = etUsername.getText().toString();
                EditText etPassword = findViewById(R.id.etPassword);
                String password = etPassword.getText().toString();

                Log.d("BookingAppTim", username);


                if((username.equals("aleksa@gmail.com") && password.equals("abc")) || (username.equals("a") && password.equals("abc"))){
                    Intent intent = new Intent(LoginActivity.this, GuestMainActivity.class);
                    startActivity(intent);;
                }
                if(username.equals("popovic.sv4.2021@uns.ac.rs") && password.equals("abc")){
                    Intent intent = new Intent(LoginActivity.this, HostMainActivity.class);
                    startActivity(intent);
                }
            }
        });

        Button btnRegister= findViewById(R.id.tvRegister);
        btnRegister.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);

            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.d("BookingAppTim", "LoginActivity onStart()");
    }
    /*
     * onResume se poziva kada je aktivnost u fokusu i korisnik
     * je u interakciji sa aktivnosti.
     * */
    @Override
    protected void onResume(){
        super.onResume();
        Log.d("BookingAppTim", "LoginActivity onResume()");
    }

    /*
     * onPause se poziva kada je aktivnost delimicno prekrivena.
     * */
    @Override
    protected void onPause() {
        super.onPause();
        Log.d("BookingAppTim", "LoginActivity onPause()");
    }
    /*
     * onStop se poziva kada je aktivnost u potpunosti prekrivena nekom drugom aktivnošću
     * */
    @Override
    protected void onStop() {
        super.onStop();
        Log.d("BookingAppTim", "LoginActivity onStop()");
    }
    /*
     * onDestory se poziva kada je aktivnost u potpunosti unistena,
     * ondosno kada je aplikacija zatvorena
     * Izbrisana je iz background-a.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("BookingAppTim", "LoginActivity onDestroy()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("BookingAppTim", "LoginActivity onRestart()");
    }
}