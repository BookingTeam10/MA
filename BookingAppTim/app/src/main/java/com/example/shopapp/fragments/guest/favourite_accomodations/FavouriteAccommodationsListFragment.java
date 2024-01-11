package com.example.shopapp.fragments.guest.favourite_accomodations;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.shopapp.adapters.AccomodationListAdapter;
import com.example.shopapp.databinding.FragmentProductsListBinding;
import com.example.shopapp.model.accommodation.Accommodation;

import java.util.ArrayList;
import java.util.List;

public class FavouriteAccommodationsListFragment extends ListFragment {
    private AccomodationListAdapter adapter;
    private FragmentProductsListBinding binding;
    private FavouriteAccommodationPageViewModel productsViewModel;
    private ArrayList<Accommodation> accommodations = new ArrayList<>();

    public static FavouriteAccommodationsListFragment newInstance(ArrayList<Accommodation> accommodations){
        FavouriteAccommodationsListFragment fragment = new FavouriteAccommodationsListFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("ShopApp", "onCreateView Products List Fragment");
        binding = FragmentProductsListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        productsViewModel = new ViewModelProvider(this).get(FavouriteAccommodationPageViewModel.class);
        adapter = new AccomodationListAdapter(getActivity(), getChildFragmentManager(), new ArrayList<>());
        setListAdapter(adapter);

        productsViewModel.getFavouriteAccommodations(3L);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("ShopApp", "onCreate Products List Fragment");
        this.getListView().setDividerHeight(2);

    }
    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
    }

}
