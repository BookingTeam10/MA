package com.example.bookingapptim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class HomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        Button btnInternet = findViewById(R.id.btnInternet);
        btnInternet.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.booking.com"));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("BookingAppTim", "HomeActivity onStart()");
    }
    /*
     * onResume se poziva kada je aktivnost u fokusu i korisnik
     * je u interakciji sa aktivnosti.
     * */
    @Override
    protected void onResume(){
        super.onResume();
        Log.d("BookingAppTim", "HomeActivity onResume()");
    }

    /*
     * onPause se poziva kada je aktivnost delimicno prekrivena.
     * */
    @Override
    protected void onPause() {
        super.onPause();
        Log.d("BookingAppTim", "HomeActivity onPause()");
    }
    /*
     * onStop se poziva kada je aktivnost u potpunosti prekrivena nekom drugom aktivnošću
     * */
    @Override
    protected void onStop() {
        super.onStop();
        Log.d("BookingAppTim", "HomeActivity onStop()");
    }
    /*
     * onDestory se poziva kada je aktivnost u potpunosti unistena,
     * ondosno kada je aplikacija zatvorena
     * Izbrisana je iz background-a.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("BookingAppTim", "HomeActivity onDestroy()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("BookingAppTim", "HomeActivity onRestart()");
    }
}