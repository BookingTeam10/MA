package com.example.shopapp.activities.GuestScreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.shopapp.R;
import com.example.shopapp.databinding.ActivityHomeBinding;
import com.google.android.material.navigation.NavigationView;

import java.util.HashSet;
import java.util.Set;

//SVE STO JE ZAKOMENTARISANO NE BRISATI
public class GuestMainActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;
    //SVE STO JE ZAKOMENTARISANO NE BRISATI
    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private NavController navController;
    private Toolbar toolbar;
    private ActionBar actionBar;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Set<Integer> topLevelDestinations = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("ShopApp", "HomeActivity onCreate()");

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        drawer = binding.drawerLayout;
        navigationView = binding.navView;
        toolbar = binding.activityHomeBase.toolbar;
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if(actionBar != null){

            actionBar.setDisplayHomeAsUpEnabled(false);
            //actionBar.setHomeAsUpIndicator(R.drawable.ic_hamburger);
            actionBar.setHomeButtonEnabled(true);
        }

        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        topLevelDestinations.add(R.id.nav_language);
        topLevelDestinations.add(R.id.nav_settings);
        navController = Navigation.findNavController(this, R.id.fragment_nav_content_main);
        navController.addOnDestinationChangedListener((navController, navDestination, bundle) -> {
            //ne brisi ovo mozda ce sluziti kasnije
            Log.i("ShopApp", "Destination Changed");

//            int id = navDestination.getId();
//            boolean isTopLevelDestination = topLevelDestinations.contains(id);
//            if (!isTopLevelDestination) {
//                switch (id) {
//                    case R.id.nav_products: // Replace with your actual menu item ID
//                        Toast.makeText(GuestMainActivity.this, "Products", Toast.LENGTH_SHORT).show();
//                        break;
//                    case R.id.nav_new:
//                        Toast.makeText(GuestMainActivity.this, "New product", Toast.LENGTH_SHORT).show();
//                        break;
//                    case R.id.nav_profile:
//                        Toast.makeText(GuestMainActivity.this, "Profile", Toast.LENGTH_SHORT).show();
//                        break;
//                    case R.id.nav_logout:
//                        Toast.makeText(GuestMainActivity.this, "Logout", Toast.LENGTH_SHORT).show();
//                        break;
//                }
//                drawer.closeDrawers();
//            }else{
//                switch (id) {
//                    case R.id.nav_settings:
//                        Toast.makeText(GuestMainActivity.this, "Settings", Toast.LENGTH_SHORT).show();
//                        break;
//                    case R.id.nav_language:
//                        Toast.makeText(GuestMainActivity.this, "Language", Toast.LENGTH_SHORT).show();
//                        break;
//                }
//            }
        });



        mAppBarConfiguration = new AppBarConfiguration
                .Builder(R.id.nav_products, R.id.nav_new, R.id.nav_profile, R.id.nav_logout, R.id.nav_settings, R.id.nav_language)
                .setOpenableLayout(drawer)
                .build();
        NavigationUI.setupWithNavController(navigationView, navController);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);

        ImageButton buttonOpenDetails = findViewById(R.id.buttonDetails);
        if (buttonOpenDetails != null) {
            buttonOpenDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(GuestMainActivity.this, AccommodationDetailsScreen.class);
                    startActivity(intent);
                }
            });
        } else {
            Log.e("ShopApp", "buttonDetails is not found in the current layout.");
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return NavigationUI.onNavDestinationSelected(item, navController) || super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onSupportNavigateUp() {
        navController = Navigation.findNavController(this, R.id.fragment_nav_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }
}