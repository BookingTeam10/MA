package com.example.shopapp.fragments.guest.favourite_accomodations;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.shopapp.R;
import com.example.shopapp.adapters.AccomodationListAdapter;
import com.example.shopapp.configuration.ServiceUtils;
import com.example.shopapp.databinding.FragmentProductsPageBinding;
import com.example.shopapp.fragments.FragmentTransition;
import com.example.shopapp.model.accommodation.Accommodation;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;


public class FavouriteAccommodationsPageFragment extends Fragment {

    public static ArrayList<Accommodation> accommodations = new ArrayList<Accommodation>();
    private FavouriteAccommodationPageViewModel productsViewModel;
    private FragmentProductsPageBinding binding;
    private AccomodationListAdapter adapter;

    public static FavouriteAccommodationsPageFragment newInstance() {
        return new FavouriteAccommodationsPageFragment();
    }
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        productsViewModel = new ViewModelProvider(this).get(FavouriteAccommodationPageViewModel.class);

        binding = FragmentProductsPageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        FavouriteAccommodationsListFragment accomodationsListFragment = new FavouriteAccommodationsListFragment();
        FragmentManager fragmentManager = getParentFragmentManager(); // ili getSupportFragmentManager() ako ste u FragmentActivity
        adapter = new AccomodationListAdapter(getActivity(), fragmentManager, accommodations);
        accomodationsListFragment.setListAdapter(adapter);
//        productsViewModel.getFavouriteAccommodations(3L).observe(getViewLifecycleOwner(), new Observer<ArrayList<Accommodation>>() {
//            @Override
//            public void onChanged(ArrayList<Accommodation> accommodations) {
//                adapter.setAccommodations(accommodations);
//            }
//        });
        SearchView searchView = binding.searchText;
        productsViewModel.getText().observe(getViewLifecycleOwner(), searchView::setQueryHint);

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
//            CalendarView calendarView1 = dialogView.findViewById(R.id.calendarView1);
//            CalendarView calendarView2 = dialogView.findViewById(R.id.calendarView2);

//            Button submitButton = dialogView.findViewById(R.id.submit_button);

//            submitButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int selectedRadioButtonId = accommodationRadioGroup.getCheckedRadioButtonId();
//                    RadioButton selectedRadioButton = dialogView.findViewById(selectedRadioButtonId);
//                    String accommodationType = selectedRadioButton != null ? selectedRadioButton.getText().toString() : "";
//
//                    String numberOfPeople = numberOfPeopleEditText.getText().toString();
//                    String minPrice = minPriceEditText.getText().toString();
//                    String maxPrice = maxPriceEditText.getText().toString();
//                    String amenity = spinnerAmenity.getSelectedItem().toString();
//                    boolean wifi = checkBoxWifi.isChecked();
//                    boolean ac = checkBoxAc.isChecked();
//                    boolean parking = checkBoxParking.isChecked();
//
//                    Log.i("ShopApp", "Accommodation Type: " + accommodationType);
//                    Log.i("ShopApp", "Number of People: " + numberOfPeople);
//                    Log.i("ShopApp", "Min Price: " + minPrice);
//                    Log.i("ShopApp", "Max Price: " + maxPrice);
//                    Log.i("ShopApp", "Amenity: " + amenity);
//                    Log.i("ShopApp", "WiFi: " + wifi);
//                    Log.i("ShopApp", "AC: " + ac);
//                    Log.i("ShopApp", "Parking: " + parking);
//
//                    bottomSheetDialog.dismiss();
//                }
//            });
        });

        Log.i("NOVI ACCOMMODATIONS", String.valueOf(accommodations.size()));
        FragmentTransition.to(FavouriteAccommodationsListFragment.newInstance(accommodations), getActivity(), false, R.id.scroll_products_list);

        return root;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}