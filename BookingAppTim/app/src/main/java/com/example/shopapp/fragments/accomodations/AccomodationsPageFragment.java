package com.example.shopapp.fragments.accomodations;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.shopapp.R;
import com.example.shopapp.activities.HostScreen.reports.GeneralReportActivity;
import com.example.shopapp.adapters.AccomodationListAdapter;
import com.example.shopapp.configuration.ServiceUtils;
import com.example.shopapp.databinding.FragmentProductsPageBinding;
import com.example.shopapp.fragments.FragmentTransition;
import com.example.shopapp.fragments.owner.add_accommodation.AddAccommodationFragment;
import com.example.shopapp.model.accommodation.Accommodation;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccomodationsPageFragment extends Fragment {

    public static ArrayList<Accommodation> accommodations = new ArrayList<Accommodation>();
    private AccomodationPageViewModel productsViewModel;
    private FragmentProductsPageBinding binding;
    private AccomodationListAdapter adapter;
    private SharedPreferences sharedPreferences;
    String date1 = "";
    String date2 = "";

    public static AccomodationsPageFragment newInstance() {
        return new AccomodationsPageFragment();
    }
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //productsViewModel = new ViewModelProvider(this).get(AccomodationPageViewModel.class);
        productsViewModel = new ViewModelProvider(requireActivity()).get(AccomodationPageViewModel.class);
        binding = FragmentProductsPageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        sharedPreferences = getActivity().getSharedPreferences("preferences", Context.MODE_PRIVATE);
        String role = sharedPreferences.getString("pref_role", "undefined");
        Log.i("ROLE FRAGMENT",role);
        AccomodationsListFragment accomodationsListFragment = new AccomodationsListFragment();
        FragmentManager fragmentManager = getParentFragmentManager(); // ili getSupportFragmentManager() ako ste u FragmentActivity
        adapter = new AccomodationListAdapter(getActivity(), fragmentManager, accommodations);
        accomodationsListFragment.setListAdapter(adapter);
        productsViewModel.getAccommodations().observe(getViewLifecycleOwner(), new Observer<ArrayList<Accommodation>>() {
            @Override
            public void onChanged(ArrayList<Accommodation> accommodations) {
                adapter.setAccommodations(accommodations);
            }
        });

        Button btnFilters = binding.btnFilters;
        btnFilters.setOnClickListener(v -> {
            Log.i("STARI ACCOMMODATIONS", String.valueOf(accommodations.size()));
            Log.i("ShopApp", "Bottom Sheet Dialog");
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity(), R.style.FullScreenBottomSheetDialog);
            View dialogView = getLayoutInflater().inflate(R.layout.bottom_sheet_filter, null);
            bottomSheetDialog.setContentView(dialogView);

            Spinner spinnerAmenity =  dialogView.findViewById(R.id.spinnerLocation);;
            ArrayAdapter<String> arrayAdapterAmenity = new ArrayAdapter<>(getActivity(),
                    android.R.layout.simple_spinner_item,
                    getResources().getStringArray(R.array.spinner_items));
            arrayAdapterAmenity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerAmenity.setAdapter(arrayAdapterAmenity);
            bottomSheetDialog.show();

            RadioGroup accommodationRadioGroup = dialogView.findViewById(R.id.accommodationRadioGroup);
            EditText numberOfPeopleEditText = dialogView.findViewById(R.id.numberOfPeople);
            EditText minPriceEditText = dialogView.findViewById(R.id.minPrice);
            EditText maxPriceEditText = dialogView.findViewById(R.id.maxPrice);
            CheckBox checkBoxWifi = dialogView.findViewById(R.id.checkBoxWifi);
            CheckBox checkBoxAc = dialogView.findViewById(R.id.checkBoxAc);
            CheckBox checkBoxParking = dialogView.findViewById(R.id.checkBoxParking);
            CalendarView calendarView1 = dialogView.findViewById(R.id.calendarView1);
            CalendarView calendarView2 = dialogView.findViewById(R.id.calendarView2);


            Button submitButton = dialogView.findViewById(R.id.submit_button);

            calendarView1.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                @Override
                public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                    // Implement your logic for calendarView1 date change here
                    // The month value is 0-based (i.e., 0 for January)
                    Log.i("UDJE OVDE1","UDJE OVDE1");
                    date1 = handleDateChange(calendarView1, year, month, dayOfMonth);
                    Log.i("UDJE OVDE2",date1);
                }
            });

            calendarView2.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                @Override
                public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                    // Implement your logic for calendarView2 date change here
                    date2 = handleDateChange(calendarView2, year, month, dayOfMonth);
                    Log.i("UDJE OVDE3",date2);
                }
            });

            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int selectedRadioButtonId = accommodationRadioGroup.getCheckedRadioButtonId();
                    RadioButton selectedRadioButton = dialogView.findViewById(selectedRadioButtonId);
                    String accommodationType = selectedRadioButton != null ? selectedRadioButton.getText().toString() : "";

                    String numberOfPeople = numberOfPeopleEditText.getText().toString();
                    String minPrice = minPriceEditText.getText().toString();
                    String maxPrice = maxPriceEditText.getText().toString();
                    String amenity = spinnerAmenity.getSelectedItem().toString();
                    boolean wifi = checkBoxWifi.isChecked();
                    boolean ac = checkBoxAc.isChecked();
                    boolean parking = checkBoxParking.isChecked();
                    String selectedCity = spinnerAmenity.getSelectedItem().toString();
                    Log.d("Selektiran grad", selectedCity);

                    List<String> amenities = new ArrayList<>();
                    if(wifi){
                        amenities.add("WIFI");
                    }
                    if(ac){
                        //izmeniti ovo posle ako treba
                        amenities.add("AC");
                    }
                    if(parking){
                        amenities.add("Parking");
                    }


                    Log.i("ShopApp", "Accommodation Type: " + accommodationType);
                    Log.i("ShopApp", "Number of People: " + numberOfPeople);
                    Log.i("ShopApp", "Min Price: " + minPrice);
                    Log.i("ShopApp", "Max Price: " + maxPrice);
                    Log.i("ShopApp", "Amenity: " + amenity);
                    Log.i("ShopApp", "WiFi: " + wifi);
                    Log.i("ShopApp", "AC: " + ac);
                    Log.i("ShopApp", "Parking: " + parking);

                    Log.i("ShopApp", "Datum1 " + date1);
                    Log.i("ShopApp", "Datum2 " + date2);

                    filterAccommodations(selectedCity,accommodationType,numberOfPeople,minPrice,maxPrice,amenities,date1,date2);
                    bottomSheetDialog.dismiss();
               }
           });
      });


        Button btnAddAcc = binding.btnAddAccommodation;
        btnAddAcc.setOnClickListener(v -> {

            AddAccommodationFragment fragment = new AddAccommodationFragment();
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            Fragment currentFragment = fragmentManager.findFragmentById(R.id.HomePage);
            //transaction.replace(R.id.reservation_detail, fragment);
            Log.d("CURRENT FRAGMENT", String.valueOf(currentFragment));
            if (currentFragment != null) {
                transaction.remove(currentFragment); // Uklonite fragment
                transaction.commit();
            }
            transaction.replace(R.id.HomePage, fragment);
            transaction.addToBackStack(null);
            transaction.commit();

        });

        Button btnReport = binding.viewReport;
        btnReport.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), GeneralReportActivity.class);
            getContext().startActivity(intent);
        });

        FragmentTransition.to(AccomodationsListFragment.newInstance(accommodations), getActivity(), false, R.id.scroll_products_list);
        if(!role.equals("Owner")){
            btnAddAcc.setVisibility(View.INVISIBLE);
            btnReport.setVisibility(View.INVISIBLE);
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void filterAccommodations(String selectedCity,String accommodationType, String numberOfPeople, String minPrice, String maxPrice, List<String> amenities,String date1,String date2){
        //moram tip dodati u konacnom i kalendari
        if(amenities==null){
            amenities = new ArrayList<>();
            amenities.add("");
        }
        if (amenities.size()==0){
            amenities.add("");
        }
        Call<ArrayList<Accommodation>> call = ServiceUtils.accommodationService.getSearchedAccommodations(selectedCity,date1,date2, Integer.valueOf(numberOfPeople), minPrice,maxPrice,amenities);
        call.enqueue(new Callback<ArrayList<Accommodation>>() {
            @Override
            public void onResponse(Call<ArrayList<Accommodation>> call, Response<ArrayList<Accommodation>> response) {
                if (response.code() == 200){
                    Log.d("REZ","Meesage recieved");
                    Log.d("UDJE DA FILTRIRA","UDJE DA FILTRIRA");
                    System.out.println(response.body());
                    ArrayList<Accommodation> accommodationsFilter = response.body();

                    productsViewModel.setAccommodations(accommodationsFilter); // filteredAccommodations je rezultat vašeg filtera
                    Log.d("SETUJE","UDJE DA FILTRIRA");
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Accommodation>> call, Throwable t) {
                Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
            }
        });

    }

    private String handleDateChange(CalendarView calendarView, int year, int month, int dayOfMonth) {

        String formattedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
        return formattedDate;
    }


}