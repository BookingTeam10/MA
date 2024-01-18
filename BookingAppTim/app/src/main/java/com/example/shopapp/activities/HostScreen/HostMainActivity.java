package com.example.shopapp.activities.HostScreen;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.shopapp.R;
import com.example.shopapp.activities.GuestScreen.GuestMainActivity;
import com.example.shopapp.activities.Login.LoginActivity;
import com.example.shopapp.activities.Notifications.GuestNotificationActivity;
import com.example.shopapp.activities.Notifications.OwnerNotificationActivity;
import com.example.shopapp.databinding.ActivityHomeBinding;
import com.example.shopapp.fragments.accomodations.AccomodationsListFragment;
import com.example.shopapp.fragments.owner.user_accommodation.UserAccommodationListFragment;
import com.example.shopapp.fragments.owner.requests.ListRequestFragment;
import com.example.shopapp.fragments.profile.ProfileFragment;
import com.google.android.material.navigation.NavigationView;

import java.util.HashSet;
import java.util.Set;

//SVE STO JE ZAKOMENTARISANO NE BRISATI
public class HostMainActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;
    //SVE STO JE ZAKOMENTARISANO NE BRISATI
    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private NavController navController;
    private Toolbar toolbar;
    private ActionBar actionBar;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private SharedPreferences sharedPreferences;
    private Set<Integer> topLevelDestinations = new HashSet<>();
    public static String SYNC_DATA = "SYNC_DATA";
    private static String OWNER_CHANNEL = "Owner channel";
    private static String GUEST_CHANNEL = "Guest channel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("ShopApp", "HomeActivity onCreate()");

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedPreferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        String role = sharedPreferences.getString("pref_role", "undefined");

        Log.i("UserRoleOWNER", "Role: " + role);

        drawer = binding.drawerLayout;
        navigationView = binding.navView;
        toolbar = binding.activityHomeBase.toolbar;
        MenuItem reservationsMenuItem = navigationView.getMenu().findItem(R.id.nav_reservations);
//        reservationsMenuItem.setVisible(false);
        MenuItem menuItem = navigationView.getMenu().findItem(R.id.nav_requests);
        menuItem.setVisible(false);
        MenuItem favouriteItem = navigationView.getMenu().findItem(R.id.nav_favourite);
        favouriteItem.setVisible(false);
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
        topLevelDestinations.add(R.id.nav_profile);
        topLevelDestinations.add(R.id.nav_settings);
        navController = Navigation.findNavController(this, R.id.fragment_nav_content_main);
        navController.addOnDestinationChangedListener((navController, navDestination, bundle) -> {
            //ne brisi ovo mozda ce sluziti kasnije
            Log.i("ShopApp", "Destination Changed");
        });



        mAppBarConfiguration = new AppBarConfiguration
                .Builder(R.id.nav_products, R.id.nav_new, R.id.nav_profile, R.id.nav_logout, R.id.nav_settings, R.id.nav_language)
                .setOpenableLayout(drawer)
                .build();
        NavigationUI.setupWithNavController(navigationView, navController);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                MenuItem myAccommodationMenuItem = navigationView.getMenu().findItem(R.id.nav_my_accommodations);
               // MenuItem requestMenuItem = navigationView.getMenu().findItem(R.id.nav_requests);

                MenuItem reservationsMenuItem = navigationView.getMenu().findItem(R.id.nav_reservations);
                MenuItem requestMenuItem = navigationView.getMenu().findItem(R.id.nav_requests_owner);
                MenuItem logOutMenuItem = navigationView.getMenu().findItem(R.id.nav_logout);
                MenuItem profileMenuItem = navigationView.getMenu().findItem(R.id.nav_profile);
                MenuItem accommodationMenuItem=navigationView.getMenu().findItem(R.id.nav_products);
                View includedLayout = findViewById(R.id.fragment_nav_content_main);
                MenuItem notificationMenuItem = navigationView.getMenu().findItem(R.id.notifications);


                if (item.getItemId() == accommodationMenuItem.getItemId()) {
                    Intent intent=new Intent(HostMainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    return true;
                }

                if (item.getItemId() == logOutMenuItem.getItemId()) {
                    Intent intent=new Intent(HostMainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    return true;
                }

                if (item.getItemId() ==reservationsMenuItem.getItemId()) {
                    Intent intent=new Intent(HostMainActivity.this, OwnerReservationActivity.class);
                    startActivity(intent);
                    return true;
                }

                if (item.getItemId() == profileMenuItem.getItemId()) {
                    includedLayout.setVisibility(View.GONE);
                    ProfileFragment fragment = new ProfileFragment();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                    return true;
                }


                if (item.getItemId() == myAccommodationMenuItem.getItemId()) {
                    includedLayout.setVisibility(View.GONE);
                    UserAccommodationListFragment fragment = new UserAccommodationListFragment();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                    return true;
                }
                if (item.getItemId() == requestMenuItem.getItemId()) {
                    includedLayout.setVisibility(View.GONE);
                    ListRequestFragment fragment = new ListRequestFragment();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                    return true;
                }
                if (item.getItemId() == notificationMenuItem.getItemId()) {
                    Intent intent = new Intent(HostMainActivity.this, OwnerNotificationActivity.class);
                    startActivity(intent);
                    return true;
                }

                binding.drawerLayout.closeDrawer(binding.navView);

                return true;
            }
        });

        createNotificationChannelOwner();
        createNotificationChannelGuest();
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
}