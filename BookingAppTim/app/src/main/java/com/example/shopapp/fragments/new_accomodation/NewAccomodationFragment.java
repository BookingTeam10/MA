package com.example.shopapp.fragments.new_accomodation;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shopapp.R;
import com.example.shopapp.activities.HostScreen.AddAccommodationActivity;
import com.example.shopapp.activities.HostScreen.AddAccommodationScreen;
import com.example.shopapp.databinding.FragmentNewProductBinding;

public class NewAccomodationFragment extends Fragment {

    private NewAccomodationViewModel mViewModel;
    private FragmentNewProductBinding binding;

    public static NewAccomodationFragment newInstance() {
        return new NewAccomodationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(NewAccomodationViewModel.class);

        binding = FragmentNewProductBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("NewAccomodationFragment", "Dugme je kliknuto!");
                Log.d("NewAccomodationFragment", "Dugme je kliknuto111!");
                Intent intent = new Intent(getActivity(), AddAccommodationActivity.class);
                startActivity(intent);
//                AddAccommodationFragment addAccommodationFragment = new AddAccommodationFragment();
//
//                Log.d("NewAccomodationFragment", "PUKNE!");
//
//                getActivity().getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.details_fragment, addAccommodationFragment)
//                        .addToBackStack(null)  // Dodavanje u back stack (opciono)
//                        .commit();
//                Log.d("NewAccomodationFragment", "PUKNE1!");
            }
        });

        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(NewAccomodationViewModel.class);
        Log.d("Booking","KREIRALO SE");
        // TODO: Use the ViewModel
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}