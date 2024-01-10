package com.example.shopapp.fragments.reservations.myReservations;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.shopapp.R;
import com.example.shopapp.adapters.ReservationListAdapter;
import com.example.shopapp.databinding.FragmentProductsPageBinding;
import com.example.shopapp.fragments.FragmentTransition;
import com.example.shopapp.model.reservation.Reservation;

import java.util.ArrayList;

public class MyReservationPageFragment extends Fragment {

    public static ArrayList<Reservation> reservations = new ArrayList<Reservation>();
    private MyReservationPageViewModel reservationPageViewModel;
    private FragmentProductsPageBinding binding;        //pro

    private ReservationListAdapter adapter;

    public static MyReservationPageFragment newInstance() {
        return new MyReservationPageFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        reservationPageViewModel = new ViewModelProvider(this).get(MyReservationPageViewModel.class);

        binding = FragmentProductsPageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        FragmentTransition.to(MyReservationListFragment.newInstance(reservations), getActivity(),
                false, R.id.scroll_products_list);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}
