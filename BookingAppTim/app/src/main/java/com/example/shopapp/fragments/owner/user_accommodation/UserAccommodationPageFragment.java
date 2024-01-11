package com.example.shopapp.fragments.owner.user_accommodation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

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

import com.example.shopapp.R;
import com.example.shopapp.adapters.AccomodationListAdapter;
import com.example.shopapp.databinding.FragmentProductsPageBinding;
import com.example.shopapp.fragments.FragmentTransition;
import com.example.shopapp.fragments.accomodations.AccomodationPageViewModel;
import com.example.shopapp.fragments.accomodations.AccomodationsListFragment;
import com.example.shopapp.fragments.accomodations.AccomodationsPageFragment;
import com.example.shopapp.model.accommodation.Accommodation;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;


public class UserAccommodationPageFragment extends Fragment {

    public static ArrayList<Accommodation> accommodations = new ArrayList<Accommodation>();
    private UserAccommodationPageViewModel productsViewModel;
    private FragmentProductsPageBinding binding;
    private AccomodationListAdapter adapter;

    public static UserAccommodationPageViewModel newInstance() {
        return new UserAccommodationPageViewModel();
    }


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        productsViewModel = new ViewModelProvider(this).get(UserAccommodationPageViewModel.class);
        binding = FragmentProductsPageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        UserAccommodationListFragment accomodationsListFragment = new UserAccommodationListFragment();
        Button addButton = root.findViewById(R.id.addButton);
        FragmentManager fragmentManager = getParentFragmentManager();
        adapter = new AccomodationListAdapter(getActivity(), fragmentManager, accommodations);
        accomodationsListFragment.setListAdapter(adapter);
        productsViewModel.getAccommodations().observe(getViewLifecycleOwner(), new Observer<ArrayList<Accommodation>>() {
            @Override
            public void onChanged(ArrayList<Accommodation> accommodations) {
                adapter.setAccommodations(accommodations);
            }
        });

        FragmentTransition.to(UserAccommodationListFragment.newInstance(accommodations), getActivity(), false, R.id.scroll_products_list);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}