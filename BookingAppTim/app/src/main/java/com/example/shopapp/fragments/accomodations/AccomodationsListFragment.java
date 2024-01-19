package com.example.shopapp.fragments.accomodations;

import static androidx.core.app.ActivityCompat.recreate;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.ListFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.shopapp.R;
import com.example.shopapp.adapters.AccomodationListAdapter;
import com.example.shopapp.configuration.ServiceUtils;
import com.example.shopapp.databinding.FragmentProductsListBinding;
import com.example.shopapp.model.accommodation.Accommodation;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccomodationsListFragment extends ListFragment implements SensorEventListener {
    private AccomodationListAdapter adapter;
    private FragmentProductsListBinding binding;
    private AccomodationPageViewModel productsViewModel;
    private ArrayList<Accommodation> accommodations = new ArrayList<>();

    //za senzore sluzi
    private SensorManager sensorManager;
    private static final int SHAKE_THRESHOLD = 800;
    private long lastUpdate;
    private float last_x;
    private float last_y;
    private float last_z;
    private SharedPreferences sharedPreferences;
    private Sensor lightSensor;
    private boolean isNightMode = false;
    private boolean isAscending = true;
    public static AccomodationsListFragment newInstance(ArrayList<Accommodation> accommodations){
        AccomodationsListFragment fragment = new AccomodationsListFragment();

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("ShopApp", "onCreateView Products List Fragment");
        binding = FragmentProductsListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        //potencijalno obrisati
        sharedPreferences = requireActivity().getSharedPreferences("pref_file", Context.MODE_PRIVATE);


        productsViewModel = new ViewModelProvider(requireActivity()).get(AccomodationPageViewModel.class);
        adapter = new AccomodationListAdapter(getActivity(), getChildFragmentManager(), new ArrayList<>());
        setListAdapter(adapter);


        productsViewModel.getAccommodations().observe(getViewLifecycleOwner(), new Observer<List<Accommodation>>() {
            @Override
            public void onChanged(List<Accommodation> newAccommodations) {
                int size = newAccommodations.size();
                accommodations = (ArrayList<Accommodation>) newAccommodations;
                adapter.setAccommodations((ArrayList<Accommodation>) newAccommodations);
                adapter.clear();
                adapter.addAll(newAccommodations);
                adapter.notifyDataSetChanged();
            }
        });


        productsViewModel.getDataFromClient();
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
        sensorManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        sensorManager.unregisterListener(this);
    }
    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            long curTime = System.currentTimeMillis();
            if ((curTime - lastUpdate) > 200) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;

                float[] values = sensorEvent.values;
                float x = values[0];
                float y = values[1];
                float z = values[2];

                float speed = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 10000;
                if (speed > SHAKE_THRESHOLD) {
                    if (accommodations != null) {
                        ArrayList<Accommodation> newList = new ArrayList<>();

                        if(isAscending){
                            sortList(newList, Comparator.reverseOrder());
                            accommodations.clear();
                            accommodations.addAll(newList);
                            adapter.notifyDataSetChanged();
                            isAscending = !isAscending;
                        }else{
                            sortList(newList, Comparator.naturalOrder());
                            accommodations.clear();
                            accommodations.addAll(newList);
                            adapter.notifyDataSetChanged();
                            isAscending = !isAscending;
                        }

                    }
                }

                //Log.d("REZ", "shake detected w/ speed: " + speed);

                last_x = x;
                last_y = y;
                last_z = z;

            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        if(sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            Log.i("REZ_ACCELEROMETER", String.valueOf(accuracy));
        }
    }

    public void sortList(ArrayList<Accommodation> newList, Comparator<? super String> keyComparator){
        newList.addAll(accommodations.stream()
                .sorted(Comparator.comparing(Accommodation::getName, keyComparator))
                .collect(Collectors.toList()));
    }
}
