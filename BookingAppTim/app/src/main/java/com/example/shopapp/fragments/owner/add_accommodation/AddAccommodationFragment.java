package com.example.shopapp.fragments.owner.add_accommodation;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.shopapp.R;
import com.example.shopapp.configuration.ServiceUtils;
import com.example.shopapp.dto.ReviewDTO;
import com.example.shopapp.enums.AccommodationStatus;
import com.example.shopapp.enums.ReservationStatus;
import com.example.shopapp.enums.ReviewStatus;
import com.example.shopapp.enums.TypeAccommodation;
import com.example.shopapp.model.accommodation.Accommodation;
import com.example.shopapp.model.accommodation.Amenity;
import com.example.shopapp.model.accommodation.Location;
import com.example.shopapp.model.accommodation.Price;
import com.example.shopapp.model.accommodation.TakenDate;
import com.example.shopapp.model.reservation.Reservation;
import com.example.shopapp.model.review.Review;
import com.example.shopapp.model.user.Owner;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddAccommodationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddAccommodationFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int STORAGE_REQUEST_CODE = 101;
    private static final int IMAGE_PICK_CAMERA_CODE = 102;
    private static final int IMAGE_PICK_GALLERY_CODE = 103;

    private String mParam1;
    private String mParam2;

    private Date beginDate = null;

    private Date endDate = null;

    public AddAccommodationFragment() {

    }

    public static AddAccommodationFragment newInstance(String param1, String param2) {
        AddAccommodationFragment fragment = new AddAccommodationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_accommodation2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button addPriceDateButton = view.findViewById(R.id.addPriceDate);

        Button addAccommodationButton = view.findViewById(R.id.addAccommodationFr);

        ArrayList<String> photos=new ArrayList<String>();
        ArrayList<Price> prices=new ArrayList<Price>();
        ArrayList<TakenDate> takenDates=new ArrayList<TakenDate>();
        ArrayList<Amenity> amenities=new ArrayList<Amenity>();
        ArrayList<Reservation> reservations=new ArrayList<Reservation>();

        addAccommodationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("RateOwnerButton", "Dugme rateOwner je kliknuto.");
                EditText nameAccommodation = view.findViewById(R.id.nazivEditText);
                String textName = nameAccommodation.getText().toString();

                EditText describe = view.findViewById(R.id.opisEditText);
                String textDescribe = describe.getText().toString();

                EditText country = view.findViewById(R.id.drzavaEditText);
                String textCountry = country.getText().toString();

                EditText city = view.findViewById(R.id.gradEditText);
                String textCity = city.getText().toString();

                EditText street = view.findViewById(R.id.ulicaEditText);
                String textStreet = street.getText().toString();

                EditText number = view.findViewById(R.id.brojEditText);
                String textNumber = number.getText().toString();
                int intNumber = Integer.parseInt(textNumber);

                Location l=new Location(1L,textCountry,textCity,textStreet,intNumber);

                CheckBox wifiCheckBox = view.findViewById(R.id.wifiCheckBox);
                CheckBox acCheckBox = view.findViewById(R.id.acCheckBox);
                CheckBox parkingCheckBox = view.findViewById(R.id.parkingCheckBox);
                if (wifiCheckBox.isChecked()) {
                    Amenity a=new Amenity("WI FI");
                    amenities.add(a);
                }
                if (acCheckBox.isChecked()) {
                    Amenity a=new Amenity("AC");
                    amenities.add(a);
                }
                if (parkingCheckBox.isChecked()) {
                    Amenity a=new Amenity("Parking");
                    amenities.add(a);
                }

                EditText minPeople = view.findViewById(R.id.minBrojGostijuEditText);
                String textminPeople = minPeople.getText().toString();
                int intminPeople = Integer.parseInt(textminPeople);

                EditText maxPeople = view.findViewById(R.id.maxBrojGostijuEditText);
                String textmaxPeople = maxPeople.getText().toString();
                int intmaxPeople = Integer.parseInt(textmaxPeople);



                RadioGroup radioGroup = view.findViewById(R.id.myRadioGroup);
                int selectedId = radioGroup.getCheckedRadioButtonId();
                if(selectedId==-1 || intminPeople>intmaxPeople){
                    new AlertDialog.Builder(getContext())
                            .setTitle("Type Unavailable")
                            .setMessage("Error!")
                            .setPositiveButton(android.R.string.ok, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }else{
                    RadioButton radioButton = view.findViewById(selectedId);
                    String selectedValue = radioButton.getText().toString();

                    TypeAccommodation type=TypeAccommodation.APARTMENT;


                    Accommodation accommodation=new Accommodation(100L,false,false,textDescribe,intminPeople,intmaxPeople,textName,photos,TypeAccommodation.APARTMENT,0,0,prices,takenDates,amenities,l,new Owner(1L),reservations,0,0,0,true, AccommodationStatus.CREATED);
                    Log.d("ACCOMMODATION DODATO",accommodation.toString());
                    Call<Accommodation> call = ServiceUtils.accommodationService.createAccommodation(accommodation);

                    call.enqueue(new Callback<Accommodation>() {
                        @Override
                        public void onResponse(Call<Accommodation> call, Response<Accommodation> response) {
                            Log.d("RESPONSE CODE AAA", String.valueOf(response.code()));
                            if (response.code() == 200){
                                Log.d("REZ","Meesage recieved");
                                System.out.println(response.body());
                                Accommodation product1 = response.body();
                                System.out.println(product1);
                                getActivity().getSupportFragmentManager().popBackStack();
                            }else{
                                Log.d("REZ","Meesage recieved: "+response.code());
                            }
                        }

                        @Override
                        public void onFailure(Call<Accommodation> call, Throwable t) {
                            Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
                        }
                    });


                }
            }
        });

        addPriceDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("RateOwnerButton", "Dugme rateOwner je kliknuto.");
                EditText nameAccommodation = view.findViewById(R.id.priceuEditText);
                String textName = nameAccommodation.getText().toString();
                double priceInput=Double.parseDouble(textName);


                CalendarView calendarViewBegin = view.findViewById(R.id.calendarView1);
                calendarViewBegin.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, month, dayOfMonth);
                        beginDate = calendar.getTime();
                    }
                });

                CalendarView calendarViewEnd = view.findViewById(R.id.calendarView2);
                calendarViewEnd.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, month, dayOfMonth);
                        endDate = calendar.getTime();
                    }
                });

                TakenDate takenDate=new TakenDate(beginDate,endDate);
                takenDates.add(takenDate);
                Price price=new Price(100L,priceInput,beginDate,endDate);
                prices.add(price);

            }
        });

        Button chooseImageButton = view.findViewById(R.id.buttonChooseImage);
        chooseImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImagePickDialog();
            }
        });
    }

    private void showImagePickDialog() {
        String[] options = {"Koristi kameru", "Izaberi iz galerije"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Izaberite sliku pomoću");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    // Koristi kameru
                    if (checkCameraPermission()) {
                        openCamera();
                    } else {
                        requestCameraPermission();
                    }
                } else {
                    // Izaberi iz galerije
                    if (checkStoragePermission()) {
                        pickFromGallery();
                    } else {
                        requestStoragePermission();
                    }
                }
            }
        });
        builder.create().show();
    }
    private boolean checkCameraPermission() {
        boolean result = ContextCompat.checkSelfPermission(getContext(),
                android.Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(getContext(),
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }

    private void requestCameraPermission() {
        // Zahtevanje dozvola za kameru
        requestPermissions(new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                CAMERA_REQUEST_CODE);
    }

    private boolean checkStoragePermission() {
        // Provera da li je dozvola za pristup skladištu odobrena
        return ContextCompat.checkSelfPermission(getContext(),
                android.Manifest.permission.READ_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
    }

    private void requestStoragePermission() {
        // Zahtevanje dozvola za pristup skladištu
        requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                STORAGE_REQUEST_CODE);
    }

    private void openCamera() {
        // Intent za otvaranje kamere
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera");
        //image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(cameraIntent, IMAGE_PICK_CAMERA_CODE);
    }

    private void pickFromGallery() {
        // Intent za otvaranje galerije
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, IMAGE_PICK_GALLERY_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CAMERA_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean writeStorageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted && writeStorageAccepted) {
                        openCamera();
                    } else {
                        // prikažite poruku o nedostatku dozvola
                    }
                }
                break;
            case STORAGE_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickFromGallery();
                } else {
                    // prikažite poruku o nedostatku dozvola
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_PICK_GALLERY_CODE) {
                // slika je izabrana iz galerije, dobijte uri slike
            } else if (requestCode == IMAGE_PICK_CAMERA_CODE) {
                // slika je snimljena kamerom, dobijte uri slike
            }
        }
    }

}