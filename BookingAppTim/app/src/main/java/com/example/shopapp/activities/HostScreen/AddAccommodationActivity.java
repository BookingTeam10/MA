package com.example.shopapp.activities.HostScreen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shopapp.R;
import com.example.shopapp.fragments.new_accomodation.EditAccommodationActivity;

public class AddAccommodationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add_accommodation);

//        EditText e=findViewById(R.id.WeekendText);
//        e.setText(0);

        Button btnAdd= findViewById(R.id.addAccommodationFr);

        btnAdd.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddAccommodationActivity.this, LoadAccSreen.class);
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
