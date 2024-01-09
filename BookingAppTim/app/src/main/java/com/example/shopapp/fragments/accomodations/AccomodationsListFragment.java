package com.example.shopapp.fragments.accomodations;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.shopapp.adapters.AccomodationListAdapter;
import com.example.shopapp.configuration.ServiceUtils;
import com.example.shopapp.databinding.FragmentProductsListBinding;
import com.example.shopapp.model.accommodation.Accommodation;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccomodationsListFragment extends ListFragment {
    private AccomodationListAdapter adapter;
    private FragmentProductsListBinding binding;
    private AccomodationPageViewModel productsViewModel;
    private ArrayList<Accommodation> accommodations = new ArrayList<>();

    public static AccomodationsListFragment newInstance(ArrayList<Accommodation> accommodations){
        AccomodationsListFragment fragment = new AccomodationsListFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("ShopApp", "onCreateView Products List Fragment");
        productsViewModel = new ViewModelProvider(this).get(AccomodationPageViewModel.class);
        binding = FragmentProductsListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        getDataFromClient();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("ShopApp", "onCreate Products List Fragment");
        this.getListView().setDividerHeight(2);
        productsViewModel.getAccommodations().observe(getViewLifecycleOwner(), newAccommodations -> {
            if (newAccommodations != null && !newAccommodations.isEmpty()) {
                adapter = new AccomodationListAdapter(getActivity(), getActivity().getSupportFragmentManager(), newAccommodations);
                setListAdapter(adapter);
            }
        });
        getDataFromClient();
    }

    @Override
    public void onResume() {
        super.onResume();
        //getDataFromClient();

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
    private void getDataFromClient(){
        Log.d("UCITA SE",ServiceUtils.SERVICE_API_PATH);
        Call<ArrayList<Accommodation>> call = ServiceUtils.accommodationService.getAll();

        call.enqueue(new Callback<ArrayList<Accommodation>>() {
            @Override
            public void onResponse(Call<ArrayList<Accommodation>> call, Response<ArrayList<Accommodation>> response) {
                if (response.code() == 200){
                    Log.d("REZ","Meesage recieved");
                    System.out.println(response.body());
                    accommodations = response.body();


//                    adapter = new AccomodationListAdapter(getActivity(), getActivity().getSupportFragmentManager(), accommodations);
//                    setListAdapter(adapter);
//                    adapter.notifyDataSetChanged();
                    productsViewModel.setAccommodations(accommodations);
                    Log.i("SMESTAJ1234", String.valueOf(productsViewModel.getAccommodations().getValue().size()));
                    StringBuilder builder = new StringBuilder();
                    for(Accommodation accommodation : accommodations) {
                        builder.append(accommodation.toString()); // Pretpostavimo da klasa Accommodation ima metod toString()
                        builder.append(", "); // Dodajemo separator
                    }
                    Log.i("SMESTAJ123", builder.toString());

                }
            }

            @Override
            public void onFailure(Call<ArrayList<Accommodation>> call, Throwable t) {
                Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
            }
        });

    }

}
