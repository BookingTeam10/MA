package com.example.bookingapptim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int SPLASH_TIME_OUT = 5000;
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {

                Intent intent = new Intent(SplashScreen.this, LoginScreen.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("BookingAppTim", "SplashActivity onStart()");
    }
    /*
     * onResume se poziva kada je aktivnost u fokusu i korisnik
     * je u interakciji sa aktivnosti.
     * */
    @Override
    protected void onResume(){
        super.onResume();
        Log.d("BookingAppTim", "SplashActivity onResume()");
    }

    /*
     * onPause se poziva kada je aktivnost delimicno prekrivena.
     * */
    @Override
    protected void onPause() {
        super.onPause();
        Log.d("BookingAppTim", "SplashActivity onPause()");
    }
    /*
     * onStop se poziva kada je aktivnost u potpunosti prekrivena nekom drugom aktivnošću
     * */
    @Override
    protected void onStop() {
        super.onStop();
        Log.d("BookingAppTim", "SplashActivity onStop()");
    }
    /*
     * onDestory se poziva kada je aktivnost u potpunosti unistena,
     * ondosno kada je aplikacija zatvorena
     * Izbrisana je iz background-a.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("BookingAppTim", "SplashActivity onDestroy()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("BookingAppTim", "SplashActivity onRestart()");
    }
}