package com.example.shopapp.activities.HostScreen;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopapp.R;
import com.example.shopapp.adapters.ImagesAdapter;

import java.util.ArrayList;

public class AddAccommodationScreen extends AppCompatActivity {

//    private static final int PICK_IMAGE_REQUEST = 1;
//
//    private RecyclerView imagesRecyclerView;
//    private ImagesAdapter adapter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add_accommodation);
//
//        //imagesRecyclerView = findViewById(R.id.imagesRecyclerView);
//        //imagesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//        adapter = new ImagesAdapter();
//        imagesRecyclerView.setAdapter(adapter);
//
//        Button addButton = findViewById(R.id.addAccommodation);
//
//        Button addImageButton = findViewById(R.id.buttonChooseImage);
//        addButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                boolean wifi = ((CheckBox) findViewById(R.id.wifiCheckBox)).isChecked();
//                boolean ac = ((CheckBox) findViewById(R.id.acCheckBox)).isChecked();
//                boolean parking = ((CheckBox) findViewById(R.id.parkingCheckBox)).isChecked();
//
//                // Kreirajte listu ili string sa svim izabranim pogodnostima
//                ArrayList<String> amenities = new ArrayList<>();
//                if (wifi) amenities.add("WiFi");
//                if (ac) amenities.add("Klima");
//                if (parking) amenities.add("Besplatan parking");
//            }
//        });
//
//        addImageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_PICK);
//                intent.setType("image/*");
//                startActivityForResult(intent, PICK_IMAGE_REQUEST);
//            }
//        });
//
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
//            Uri selectedImage = data.getData();
//            adapter.addImage(selectedImage);
//            adapter.notifyDataSetChanged();
//        }
//    }
}
