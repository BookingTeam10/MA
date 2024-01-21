package com.example.shopapp.activities.AdminScreen;

import android.content.Intent;
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
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.shopapp.R;
import com.example.shopapp.activities.Login.LoginActivity;
import com.example.shopapp.activities.Notifications.GuestNotificationActivity;
import com.example.shopapp.databinding.ActivityHomeBinding;
import com.example.shopapp.fragments.accomodations.AccomodationsListFragment;
import com.example.shopapp.fragments.admin.AccommodationApprovalFragment;
import com.example.shopapp.fragments.admin.AccommodationRateReportFragment;
import com.example.shopapp.fragments.admin.OwnerRateReportFragment;
import com.example.shopapp.fragments.admin.UserReportsAdminFragment;
import com.example.shopapp.fragments.guest.favourite_accomodations.FavouriteAccommodationsListFragment;
import com.example.shopapp.fragments.guest.reservations.RequestFragment;
import com.example.shopapp.fragments.guest.reservations.myReservations.MyReservationListFragment;
import com.example.shopapp.fragments.profile.ProfileFragment;
import com.google.android.material.navigation.NavigationView;

import java.util.HashSet;
import java.util.Set;

//SVE STO JE ZAKOMENTARISANO NE BRISATI
public class AdminMainActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;
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
        //navigationView = binding.navView;
        NavigationView navigationView = findViewById(R.id.nav_view);
        toolbar = binding.activityHomeBase.toolbar;

        //ne treba
        //
        //2 active reservation, notifikaije i favourtie
        MenuItem myAccommodationMenuItem = navigationView.getMenu().findItem(R.id.nav_reservations);
        myAccommodationMenuItem.setVisible(false);
        MenuItem menuItem = navigationView.getMenu().findItem(R.id.nav_requests);
        menuItem.setVisible(false);
        MenuItem myAccommodationMenuItem1 = navigationView.getMenu().findItem(R.id.nav_my_accommodations);
        myAccommodationMenuItem1.setVisible(false);
        MenuItem menuItem2 = navigationView.getMenu().findItem(R.id.nav_requests_owner);
        menuItem2.setVisible(false);
        MenuItem myAccommodationMenuItem3 = navigationView.getMenu().findItem(R.id.nav_new);
        myAccommodationMenuItem3.setVisible(false);
        MenuItem myAccommodationMenuItem4 = navigationView.getMenu().findItem(R.id.nav_owner_reservations);
        myAccommodationMenuItem4.setVisible(false);
        MenuItem menuItem5 = navigationView.getMenu().findItem(R.id.nav_guest_reservations);
        menuItem5.setVisible(false);
        MenuItem myAccommodationMenuItem6 = navigationView.getMenu().findItem(R.id.nav_favourite);
        myAccommodationMenuItem6.setVisible(false);
        MenuItem menuItem7 = navigationView.getMenu().findItem(R.id.notifications);
        menuItem7.setVisible(false);

        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if(actionBar != null){

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
        topLevelDestinations.add(R.id.nav_approval_accommodation);
        navController = Navigation.findNavController(this, R.id.fragment_nav_content_main);
        navController.addOnDestinationChangedListener((navController, navDestination, bundle) -> {
            //ne brisi ovo mozda ce sluziti kasnije
            Log.i("ShopApp", "Destination Changed");
        });

        mAppBarConfiguration = new AppBarConfiguration
                .Builder(R.id.nav_products, R.id.nav_new,R.id.nav_requests, R.id.nav_profile, R.id.nav_logout, R.id.nav_settings, R.id.nav_language, R.id.nav_approval_accommodation)
                .setOpenableLayout(drawer)
                .build();
        NavigationUI.setupWithNavController(navigationView, navController);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                MenuItem reservationsMenuItem = navigationView.getMenu().findItem(R.id.nav_reservations);
                MenuItem requestMenuItem = navigationView.getMenu().findItem(R.id.nav_requests);
                MenuItem logOutMenuItem = navigationView.getMenu().findItem(R.id.nav_logout);
                MenuItem profileMenuItem = navigationView.getMenu().findItem(R.id.nav_profile);
                MenuItem favouriteMenuItem = navigationView.getMenu().findItem(R.id.nav_favourite);
                MenuItem homeMenuItem = navigationView.getMenu().findItem(R.id.nav_products);
                MenuItem notificationMenuItem = navigationView.getMenu().findItem(R.id.notifications);
                MenuItem userReports = navigationView.getMenu().findItem(R.id.nav_user_reports);
                MenuItem accommodationReviews = navigationView.getMenu().findItem(R.id.nav_acc_reviews);
                MenuItem approveAccommodationMenuItem = navigationView.getMenu().findItem(R.id.nav_approval_accommodation);
                MenuItem ownerReviews = navigationView.getMenu().findItem(R.id.nav_owner_reviews);
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
                    Intent intent = new Intent(AdminMainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    return true;
                }

                if (item.getItemId() == approveAccommodationMenuItem.getItemId()) {
                    AccommodationApprovalFragment fragment = new AccommodationApprovalFragment();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                    return true;
                }

                if (item.getItemId() == accommodationReviews.getItemId()) {
                    AccommodationRateReportFragment fragment = new AccommodationRateReportFragment();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                    return true;
                }

                if (item.getItemId() == ownerReviews.getItemId()) {
                    OwnerRateReportFragment fragment = new OwnerRateReportFragment();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                    return true;
                }


                if (item.getItemId() == userReports.getItemId()) {
                    UserReportsAdminFragment fragment = new UserReportsAdminFragment();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
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
                    Intent intent = new Intent(AdminMainActivity.this, GuestNotificationActivity.class);
                    startActivity(intent);
                    return true;
                }

                binding.drawerLayout.closeDrawer(binding.navView);

                return true;
            }
        });
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