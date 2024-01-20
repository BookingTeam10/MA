package com.example.shopapp.fragments.guest.reservation_management;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopapp.R;
import com.example.shopapp.adapters.OwnerReservationListAdapter;
import com.example.shopapp.configuration.ServiceUtils;
import com.example.shopapp.enums.ReservationStatus;
import com.example.shopapp.model.reservation.Reservation;
import com.example.shopapp.model.user.Guest;
import com.example.shopapp.model.user.Owner;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GuestReservationFragment extends Fragment implements OwnerReservationListAdapter.OnButtonClickListener {

    private List<Reservation> reservations;
    private Guest guest;
    private OwnerReservationListAdapter adapter;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_owner_reservations, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);

        loadReservations();
    }

    private void loadReservations() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            guest = bundle.getParcelable("guest");
        }

        Call<ArrayList<Reservation>> reservationsCall = ServiceUtils.reservationService.getGuestRequests(guest.getId());

        reservationsCall.enqueue(new Callback<ArrayList<Reservation>>() {
            @Override
            public void onResponse(Call<ArrayList<Reservation>> call, Response<ArrayList<Reservation>> response) {
                if(response.isSuccessful()){
                    Date today = new Date();
                    today = resetTimeToMidnight(today);

                    Date finalToday = today;
                    reservations = response.body().stream()
                            .filter(reservation -> {
                                Date reservationStartDate = resetTimeToMidnight(reservation.getStartDate());

                                return reservation.getStatus() == ReservationStatus.ACCEPTED
                                        && reservationStartDate.after(finalToday);
                            })
                            .collect(Collectors.toList());
                    adapter = new OwnerReservationListAdapter(reservations, GuestReservationFragment.this);
                    adapter.isGuest = true;
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                }
            }

            private Date resetTimeToMidnight(Date date) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                try {
                    return sdf.parse(sdf.format(date));
                } catch (Exception e) {
                    e.printStackTrace();
                    return date;
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Reservation>> call, Throwable t) {

            }
        });

    }


    @Override
    public void onDeleteClick(int position) {
    }

    @Override
    public void onApproveClick(int position) {
    }

    @Override
    public void onCancelClick(int position) {
        Reservation reservation = reservations.get(position);
        Date startDate = reservation.getStartDate();
        int cancelDeadlineInDays = reservation.getAccommodation().getCancelDeadline();


        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.DAY_OF_MONTH, -cancelDeadlineInDays);
        Date deadlineDate = calendar.getTime();
        Date today = new Date();

        if(today.before(deadlineDate)){
            Call<Void> cancelReservationCall = ServiceUtils.reservationService.cancelReservation(reservation.getId(), new Reservation(), false);

            cancelReservationCall.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if(response.isSuccessful()){
                        loadReservations();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });
        }else{
            Toast.makeText(getContext(), "Deadline passed", Toast.LENGTH_SHORT).show();
        }
    }
}
