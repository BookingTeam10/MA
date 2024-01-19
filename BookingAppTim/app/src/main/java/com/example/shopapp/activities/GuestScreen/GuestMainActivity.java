package com.example.shopapp.activities.GuestScreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.shopapp.R;
import com.example.shopapp.activities.Login.LoginActivity;
import com.example.shopapp.activities.Notifications.GuestNotificationActivity;
import com.example.shopapp.databinding.ActivityHomeBinding;
import com.example.shopapp.fragments.accomodations.AccomodationsListFragment;
import com.example.shopapp.fragments.accomodations.AccomodationsPageFragment;
import com.example.shopapp.fragments.guest.favourite_accomodations.FavouriteAccommodationsListFragment;
import com.example.shopapp.fragments.guest.favourite_accomodations.FavouriteAccommodationsPageFragment;
import com.example.shopapp.fragments.profile.ProfileFragment;
import com.example.shopapp.fragments.guest.reservations.RequestFragment;
import com.example.shopapp.fragments.guest.reservations.myReservations.MyReservationListFragment;
import com.example.shopapp.services.GuestMessageService;
import com.google.android.material.navigation.NavigationView;
import java.util.HashSet;
import java.util.Set;

public class GuestMainActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;
    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private NavController navController;
    private Toolbar toolbar;
    private ActionBar actionBar;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Set<Integer> topLevelDestinations = new HashSet<>();
    private SharedPreferences sharedPreferences;

    private SensorManager sensorManager;
    private Sensor lightSensor;
    public static String SYNC_DATA = "SYNC_DATA";
    private static String GUEST_CHANNEL = "Guest channel";
    private static String OWNER_CHANNEL = "Owner channel";

    private final SensorEventListener lightSensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float lightValue = event.values[0];
            int nightModeThreshold = 5;
            Log.d("LIGHTVALUE", String.valueOf(lightValue));
            if (lightValue < nightModeThreshold) {
                Log.d("UDJE U SENZOR","UDJE U SENZOR");
                // Aktivirajte noćni režim, ovo odkomentarisati pred odbranu
               //setTheme(R.style.ShopApp);
               // recreate();

            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // Implementirajte po potrebi
            if(sensor.getType() == Sensor.TYPE_ACCELEROMETER){
                Log.i("REZ_ACCELEROMETER", String.valueOf(accuracy));
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("ShopApp", "HomeActivity onCreate()");

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sharedPreferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        String role = sharedPreferences.getString("pref_role", "undefined");

        drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        toolbar = binding.activityHomeBase.toolbar;

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        if (lightSensor != null) {
            sensorManager.registerListener(lightSensorListener, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
        MenuItem myAccommodationMenuItem = navigationView.getMenu().findItem(R.id.nav_my_accommodations);
        myAccommodationMenuItem.setVisible(false);
        MenuItem menuItem = navigationView.getMenu().findItem(R.id.nav_requests_owner);
        menuItem.setVisible(false);
        MenuItem userReportMenuItem = navigationView.getMenu().findItem(R.id.nav_user_reports);
        userReportMenuItem.setVisible(false);
        MenuItem myReservationMenuItem = navigationView.getMenu().findItem(R.id.nav_new);
        myReservationMenuItem.setVisible(false);

        MenuItem aproveMenuItem = navigationView.getMenu().findItem(R.id.nav_approval_accommodation);
        aproveMenuItem.setVisible(false);


        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setHomeButtonEnabled(true);
        }

        actionBarDrawerToggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        topLevelDestinations.add(R.id.nav_language);
        topLevelDestinations.add(R.id.nav_requests);
        topLevelDestinations.add(R.id.nav_profile);
        topLevelDestinations.add(R.id.nav_settings);
        navController = Navigation.findNavController(this, R.id.fragment_nav_content_main);
        navController.addOnDestinationChangedListener((navController, navDestination, bundle) -> {
            //ne brisi ovo mozda ce sluziti kasnije
            Log.i("ShopApp", "Destination Changed");
        });

        mAppBarConfiguration = new AppBarConfiguration
                    .Builder(R.id.nav_products, R.id.nav_new, R.id.nav_requests, R.id.nav_profile, R.id.nav_logout, R.id.nav_settings, R.id.nav_language)
                    .setOpenableLayout(drawer)
                    .build();
        NavigationUI.setupWithNavController(navigationView, navController);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                    MenuItem reservationsMenuItem = navigationView.getMenu().findItem(R.id.nav_reservations);
                    MenuItem requestMenuItem = navigationView.getMenu().findItem(R.id.nav_requests);
                    MenuItem logOutMenuItem = navigationView.getMenu().findItem(R.id.nav_logout);
                    MenuItem profileMenuItem = navigationView.getMenu().findItem(R.id.nav_profile);
                    MenuItem favouriteMenuItem = navigationView.getMenu().findItem(R.id.nav_favourite);
                    MenuItem homeMenuItem = navigationView.getMenu().findItem(R.id.nav_products);
                    MenuItem notificationMenuItem = navigationView.getMenu().findItem(R.id.notifications);
                    View includedLayout = findViewById(R.id.fragment_nav_content_main);

                    Log.d("MENI 123", "NAPRAVILO SE");

                    if (item.getItemId() == reservationsMenuItem.getItemId()) {
                        includedLayout.setVisibility(View.GONE);
                        MyReservationListFragment fragment = new MyReservationListFragment();
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container, fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                        return true;
                    }
                    if (item.getItemId() == requestMenuItem.getItemId()) {
                        includedLayout.setVisibility(View.GONE);
                        RequestFragment fragment = new RequestFragment();
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container, fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                        return true;
                    }


                    if (item.getItemId() == logOutMenuItem.getItemId()) {
                        Intent intent = new Intent(GuestMainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        return true;
                    }

                    if (item.getItemId() == profileMenuItem.getItemId()) {
                        ProfileFragment fragment = new ProfileFragment();
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container, fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                        return true;
                    }
                    if (item.getItemId() == favouriteMenuItem.getItemId()) {
                        includedLayout.setVisibility(View.GONE);
                        //ostaviti ovako ali u AccommodationListFragmentu izmeniti da se pozove getFavouriteAccommodations
                        //AccomodationsListFragment fragment = new AccomodationsListFragment();
                        FavouriteAccommodationsListFragment fragment = new FavouriteAccommodationsListFragment();
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container, fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                        return true;
                    }
                    if (item.getItemId() == homeMenuItem.getItemId()) {
                        includedLayout.setVisibility(View.GONE);
                        //AccomodationsPageFragment fragment1 = new AccomodationsPageFragment();
                        AccomodationsListFragment fragment2 = new AccomodationsListFragment();
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container, fragment2);
                        //transaction.replace(R.id.fragment_container, fragment2);
                        transaction.addToBackStack(null);
                        transaction.commit();
                        return true;
                    }
                    if (item.getItemId() == notificationMenuItem.getItemId()) {
                        Intent intent = new Intent(GuestMainActivity.this, GuestNotificationActivity.class);
                        startActivity(intent);
                        return true;
                    }

                    // Zatvori navigacijski izbornik
                    binding.drawerLayout.closeDrawer(binding.navView);

                    return true;
                }
            });
        createNotificationChannelGuest();
        createNotificationChannelOwner();
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

    private void createNotificationChannelGuest() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Notification channel";
            String description = "Description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(GUEST_CHANNEL, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void createNotificationChannelOwner() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Notification channel";
            String description = "Description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(OWNER_CHANNEL, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void sendGuest(View v) {
        Log.d("UDJE U SEND","UDJE");
        Notification notification = new NotificationCompat.Builder(this, GUEST_CHANNEL)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Title")
                .setContentText("Message")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManagerCompat.notify(1, notification);
    }

    public void sendOwner(View v) {
        Log.d("UDJE OVDE DA POSALJE PORUKU","UDJEEE");
        Notification notification = new NotificationCompat.Builder(this,OWNER_CHANNEL)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Title")
                .setContentText("Message")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManagerCompat.notify(1, notification);

    }

}