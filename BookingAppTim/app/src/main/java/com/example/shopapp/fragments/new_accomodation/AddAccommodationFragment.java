package com.example.shopapp.fragments.new_accomodation;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.shopapp.R;
import com.example.shopapp.activities.HostScreen.LoadScreen;
import com.example.shopapp.databinding.FragmentAddAccommodationBinding;
import com.example.shopapp.databinding.FragmentNewProductBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddAccommodationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddAccommodationFragment extends Fragment {

    private AddAccommodationViewModel mViewModel;
    private FragmentAddAccommodationBinding binding;

    public static AddAccommodationFragment newInstance() {
        return new AddAccommodationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(AddAccommodationViewModel.class);

        binding = FragmentAddAccommodationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.addAccommodationFr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("NewAccomodationFragment", "Dugme je kliknutoFRAGMENT!");

                Intent intent = new Intent(getActivity(), LoadScreen.class);
                startActivity(intent);


                //LoadAccommodationFragment loadAccommodationFragment = new LoadAccommodationFragment();
                //getActivity().getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                //getActivity().getSupportFragmentManager().popBackStack();
//                getActivity().getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.details_fragment_accommodation, loadAccommodationFragment)
//                        .addToBackStack(null)  // Dodavanje u back stack (opciono)
//                        .commit();
                //getActivity().getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                //getActivity().getSupportFragmentManager().popBackStack();
                Log.d("NewAccomodationFragment", "PUKNE1!");
            }
        });

        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AddAccommodationViewModel.class);
        Log.d("Booking","KREIRALO SE");
        // TODO: Use the ViewModel
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}