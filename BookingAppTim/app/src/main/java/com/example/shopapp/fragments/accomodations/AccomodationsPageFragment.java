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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.shopapp.R;
import com.example.shopapp.databinding.FragmentProductsPageBinding;
import com.example.shopapp.fragments.FragmentTransition;
import com.example.shopapp.model.accommodation.Accommodation;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

public class AccomodationsPageFragment extends Fragment {

    public static ArrayList<Accommodation> accomodations = new ArrayList<Accommodation>();
    private AccomodationPageViewModel productsViewModel;
    private FragmentProductsPageBinding binding;

    public static AccomodationsPageFragment newInstance() {
        return new AccomodationsPageFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        productsViewModel = new ViewModelProvider(this).get(AccomodationPageViewModel.class);

        binding = FragmentProductsPageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        prepareProductList(accomodations);

        SearchView searchView = binding.searchText;
        productsViewModel.getText().observe(getViewLifecycleOwner(), searchView::setQueryHint);

        Button btnFilters = binding.btnFilters;
        btnFilters.setOnClickListener(v -> {
            Log.i("ShopApp", "Bottom Sheet Dialog");
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity(), R.style.FullScreenBottomSheetDialog);
            View dialogView = getLayoutInflater().inflate(R.layout.bottom_sheet_filter, null);
            bottomSheetDialog.setContentView(dialogView);
            //punjenje lokacija, slucajno sam nazvao Amenity, izmeniti posle
            Spinner spinnerAmenity =  dialogView.findViewById(R.id.spinnerAmenity);;
            ArrayAdapter<String> arrayAdapterAmenity = new ArrayAdapter<>(getActivity(),
                    android.R.layout.simple_spinner_item,
                    getResources().getStringArray(R.array.spinner_items));
            arrayAdapterAmenity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerAmenity.setAdapter(arrayAdapterAmenity);

            bottomSheetDialog.show();
        });

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

        //radio Button
        RadioGroup radioGroup = root.findViewById(R.id.accommodationRadioGroup); // Pretpostavljamo da je ovo vaš RadioGroup

     //  int selectedId = radioGroup.getCheckedRadioButtonId();
//
//        if (selectedId != -1) {
//            RadioButton radioButton =  root.findViewById(selectedId);
//            String selectedValue = radioButton.getText().toString();
//            Log.i("RADIO BUTTON",selectedValue);
//        } else {
//            // Nijedan RadioButton nije selektovan
//        }

        FragmentTransition.to(AccomodationsListFragment.newInstance(accomodations), getActivity(), false, R.id.scroll_products_list);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void prepareProductList(ArrayList<Accommodation> products){
        products.add(new Accommodation(1L, "Aparment in Novi Sad, Gunduliceva 21a", "This aparment in Novi Sad.", R.drawable.apartment1));
        products.add(new Accommodation(2L, "Aparment in Belgrade, Sanska 17", "This aparment in Belgrade.", R.drawable.apartment2));
    }
}