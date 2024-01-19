package com.example.shopapp.fragments.owner.user_accommodation;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.shopapp.R;
import com.example.shopapp.adapters.AccomodationListAdapter;
import com.example.shopapp.databinding.FragmentProductsListBinding;
import com.example.shopapp.fragments.accomodations.AccomodationPageViewModel;
import com.example.shopapp.fragments.accomodations.AccomodationsListFragment;
import com.example.shopapp.model.accommodation.Accommodation;
import com.example.shopapp.model.user.Guest;
import com.example.shopapp.model.user.Owner;

import java.util.ArrayList;
import java.util.List;


public class UserAccommodationListFragment extends ListFragment {

    private AccomodationListAdapter adapter;
    private FragmentProductsListBinding binding;
    private UserAccommodationPageViewModel productsViewModel;
    private ArrayList<Accommodation> accommodations = new ArrayList<>();

    public static UserAccommodationListFragment newInstance(ArrayList<Accommodation> accommodations){
        UserAccommodationListFragment fragment = new UserAccommodationListFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("ShopApp", "onCreateView Products List Fragment");
        binding = FragmentProductsListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button addButton = root.findViewById(R.id.addButton);
        productsViewModel = new ViewModelProvider(this).get(UserAccommodationPageViewModel.class);
        adapter = new AccomodationListAdapter(getActivity(), getChildFragmentManager(), new ArrayList<>(),true);
        setListAdapter(adapter);
        productsViewModel.getAccommodations().observe(getViewLifecycleOwner(), new Observer<List<Accommodation>>() {
            @Override
            public void onChanged(List<Accommodation> accommodations) {
                adapter.clear();
                adapter.addAll(accommodations);
                adapter.notifyDataSetChanged();
            }
        });


        productsViewModel.getDataByOwner();
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