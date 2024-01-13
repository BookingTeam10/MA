package com.example.shopapp.activities.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.auth0.android.jwt.JWT;
import com.example.shopapp.R;
import com.example.shopapp.activities.AdminScreen.AdminMainActivity;
import com.example.shopapp.activities.GuestScreen.GuestMainActivity;
import com.example.shopapp.activities.HostScreen.HostMainActivity;
import com.example.shopapp.configuration.ServiceUtils;
import com.example.shopapp.model.login.LoginDTO;
import com.example.shopapp.model.login.Token;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button btnLogin= findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String etUsername = ((EditText) findViewById(R.id.etUsername)).getText().toString();
                String etPassword = ((EditText) findViewById(R.id.etPassword)).getText().toString();
                login(etUsername, etPassword);
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
    public void login(String email, String password){
        LoginDTO loginDTO = new LoginDTO(email, password);
        Call<Token> call = ServiceUtils.userService.loginUser(loginDTO);

        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(@NonNull Call<Token> call, @NonNull Response<Token> response) {
                Log.d("TOKEN",response.body().toString());
                if(!response.isSuccessful()) {
                    Log.d("Login Fail", "Response error");
                    Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                    return;
                }

                Token loginResponse = response.body();
                String userRole = "";

                if(loginResponse.getJwt().equals("NEUSPESNO")){
                    Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                    return;
                }

                JWT jwt = new JWT(loginResponse.getJwt());

                List<HashMap> role =
                        jwt.getClaim("role").asList(HashMap.class);
                for (Object values: role.get(0).values()){
                    userRole = values.toString();
                    break;
                }

                String email = jwt.getClaim("sub").asString();
                Long id = jwt.getClaim("id").asLong();
                Log.d("EMAIL UCITAN", String.valueOf(email));
                Log.d("ID UCITAN", String.valueOf(id));
                //Token token = Token.getInstance();
                //token.setJwt(loginResponse.getJwt());


                //ovo ostaviti i posle roknuti
                if(userRole.equals("ROLE_Guest")){
                    setSharedPreferences("Guest", email, id);
                    //setTokenPreference(loginResponse.getToken(), loginResponse.getRefreshToken());
                    Intent intent = new Intent(LoginActivity.this,GuestMainActivity.class);
                    startActivity(intent);

                }

                else if(userRole.equals("ROLE_Owner")) {
                    setSharedPreferences("Owner", email, id);
                    //setTokenPreference(loginResponse.getToken(), loginResponse.getRefreshToken());
                    Intent intent = new Intent(LoginActivity.this,HostMainActivity.class);
                    startActivity(intent);

                }
                else if(userRole.equalsIgnoreCase("ROLE_Administrator")) {
                    //setSharedPreferences("DRIVER", email, id);
                    //setTokenPreference(loginResponse.getToken(), loginResponse.getRefreshToken());
                    Intent intent = new Intent(LoginActivity.this, AdminMainActivity.class);
                    startActivity(intent);
                }

            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Log.d("Login Failed", t.getMessage());
            }
        });
    }

    private void setSharedPreferences(String role, String email, Long id){
        this.sharedPreferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor spEditor = this.sharedPreferences.edit();
        spEditor.putString("pref_role", role);
        spEditor.putString("pref_email", email);
        //spEditor.putLong("pref_id", id);
        spEditor.apply();

    }
}