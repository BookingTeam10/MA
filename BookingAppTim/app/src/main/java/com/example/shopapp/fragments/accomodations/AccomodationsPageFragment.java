package com.example.shopapp.fragments.accomodations;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.shopapp.R;
import com.example.shopapp.adapters.AccomodationListAdapter;
import com.example.shopapp.configuration.ServiceUtils;
import com.example.shopapp.databinding.FragmentProductsPageBinding;
import com.example.shopapp.fragments.FragmentTransition;
import com.example.shopapp.model.accommodation.Accommodation;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccomodationsPageFragment extends Fragment {

    public static ArrayList<Accommodation> accommodations = new ArrayList<Accommodation>();
    private AccomodationPageViewModel productsViewModel;
    private FragmentProductsPageBinding binding;
    private AccomodationListAdapter adapter;
    public static AccomodationsPageFragment newInstance() {
        return new AccomodationsPageFragment();
    }
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        productsViewModel = new ViewModelProvider(this).get(AccomodationPageViewModel.class);

        binding = FragmentProductsPageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        SearchView searchView = binding.searchText;
        productsViewModel.getText().observe(getViewLifecycleOwner(), searchView::setQueryHint);

        Button btnFilters = binding.btnFilters;
        btnFilters.setOnClickListener(v -> {
            Log.i("ShopApp", "Bottom Sheet Dialog");
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity(), R.style.FullScreenBottomSheetDialog);
            View dialogView = getLayoutInflater().inflate(R.layout.bottom_sheet_filter, null);
            bottomSheetDialog.setContentView(dialogView);
            //punjenje lokacija, slucajno sam nazvao Amenity, izmeniti posle
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

            Button submitButton = dialogView.findViewById(R.id.submit_button);

            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Čitanje vrednosti iz RadioGroup
                    int selectedRadioButtonId = accommodationRadioGroup.getCheckedRadioButtonId();
                    RadioButton selectedRadioButton = dialogView.findViewById(selectedRadioButtonId);
                    String accommodationType = selectedRadioButton != null ? selectedRadioButton.getText().toString() : "";

                    // Čitanje ostalih vrednosti
                    String numberOfPeople = numberOfPeopleEditText.getText().toString();
                    String minPrice = minPriceEditText.getText().toString();
                    String maxPrice = maxPriceEditText.getText().toString();
                    String amenity = spinnerAmenity.getSelectedItem().toString();
                    boolean wifi = checkBoxWifi.isChecked();
                    boolean ac = checkBoxAc.isChecked();
                    boolean parking = checkBoxParking.isChecked();

                    // Ispisivanje vrednosti
                    Log.i("ShopApp", "Accommodation Type: " + accommodationType);
                    Log.i("ShopApp", "Number of People: " + numberOfPeople);
                    Log.i("ShopApp", "Min Price: " + minPrice);
                    Log.i("ShopApp", "Max Price: " + maxPrice);
                    Log.i("ShopApp", "Amenity: " + amenity);
                    Log.i("ShopApp", "WiFi: " + wifi);
                    Log.i("ShopApp", "AC: " + ac);
                    Log.i("ShopApp", "Parking: " + parking);
                    ArrayList<Accommodation> filteredAccommodations =new ArrayList<>();
                    productsViewModel.setAccommodations(filteredAccommodations);
                    Log.i("SMESTAJ12345", String.valueOf(productsViewModel.getAccommodations().getValue().size()));
                }
            });
        });

        //msm da je ovo sve visak al nek stoji
        Spinner spinner = binding.btnSort;
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.sort_array));
        // Specify the layout to use when the list of choices appears
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(arrayAdapter);



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        Spinner spinnerType = binding.btnType;
        ArrayAdapter<String> arrayType = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.type_accomodations));
        arrayType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(arrayType);
        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });


        FragmentTransition.to(AccomodationsListFragment.newInstance(accommodations), getActivity(), false, R.id.scroll_products_list);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void filterAccommodations(){
        Call<ArrayList<Accommodation>> call = ServiceUtils.accommodationService.getAll();
        call.enqueue(new Callback<ArrayList<Accommodation>>() {
            @Override
            public void onResponse(Call<ArrayList<Accommodation>> call, Response<ArrayList<Accommodation>> response) {
                if (response.code() == 200){
                    Log.d("REZ","Meesage recieved");
                    Log.d("UDJE","UDJE");
                    System.out.println(response.body());
                    //accommodations = response.body();
                    StringBuilder builder = new StringBuilder();
                    for(Accommodation accommodation : accommodations) {
                        builder.append(accommodation.toString()); // Pretpostavimo da klasa Accommodation ima metod toString()
                        builder.append(", "); // Dodajemo separator
                    }
                    Log.i("SMESTAJ", builder.toString());
                    Log.i("productsViewModel",productsViewModel.toString());
                    productsViewModel.setAccommodations(accommodations);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Accommodation>> call, Throwable t) {
                Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
            }
        });

    }

}