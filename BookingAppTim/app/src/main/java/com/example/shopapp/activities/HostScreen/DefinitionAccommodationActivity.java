package com.example.shopapp.activities.HostScreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.example.shopapp.R;
import com.example.shopapp.configuration.ServiceUtils;
import com.example.shopapp.dto.ReviewDTO;
import com.example.shopapp.enums.AccommodationStatus;
import com.example.shopapp.enums.TypeAccommodation;
import com.example.shopapp.model.accommodation.Accommodation;
import com.example.shopapp.model.accommodation.Amenity;
import com.example.shopapp.model.accommodation.Location;
import com.example.shopapp.model.accommodation.Price;
import com.example.shopapp.model.accommodation.TakenDate;
import com.example.shopapp.model.reservation.Reservation;
import com.example.shopapp.model.user.Owner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DefinitionAccommodationActivity extends AppCompatActivity {

    private EditText nameText, minPeopleText, maxPeopleText, amenityText, streetText, cityText, countryText, descriptionText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_definition_accommodation);
        Accommodation accommodation = getIntent().getParcelableExtra("accommodation");
        Log.d("ACCOMMODATION EDIT",accommodation.toString1());
        if (accommodation != null) {

            nameText = findViewById(R.id.nameText);
            minPeopleText = findViewById(R.id.minPeopleText);
            maxPeopleText = findViewById(R.id.maxPeopleText);
            amenityText = findViewById(R.id.amenityText);
            streetText = findViewById(R.id.streetText);
            cityText = findViewById(R.id.cityText);
            countryText = findViewById(R.id.countryText);
            descriptionText = findViewById(R.id.descriptionText);


            EditText weekendText = findViewById(R.id.WeekendText);
            EditText holidayText = findViewById(R.id.HolidayText);
            EditText summerText = findViewById(R.id.SummerText);
            EditText limitText = findViewById(R.id.limitText);


            RadioGroup typeRadioGroup1 = findViewById(R.id.RadioGroupDefinition1);
            RadioButton guestRadioButton = findViewById(R.id.guestRadioButtonDefinition);
            RadioButton nightRadioButton = findViewById(R.id.nightRadioButtonDefinition);

            RadioGroup typeRadioGroup2 = findViewById(R.id.RadioGroupDefinition2);
            RadioButton automaticRadioButton = findViewById(R.id.AutomaticConfirmationDefinition);
            RadioButton noAutomaticRadioButton = findViewById(R.id.NoAutomaticDefinition);

            StringBuilder ret = new StringBuilder();
            List<Amenity> amenities = accommodation.getAmenities();
            for (int i = 0; i < amenities.size() - 1; i++) {
                ret.append(amenities.get(i).getName()).append(", ");
            }

            ret.append(amenities.get(amenities.size() - 1).getName());
            String amen = ret.toString();

            nameText.setText(accommodation.getName());
            minPeopleText.setText(String.valueOf(accommodation.getMinPeople()));
            maxPeopleText.setText(String.valueOf(accommodation.getMaxPeople()));
            amenityText.setText(amen);
            streetText.setText(accommodation.getLocation().getStreet() + " " + String.valueOf(accommodation.getLocation().getNumber()));
            cityText.setText(accommodation.getLocation().getCity());
            countryText.setText(accommodation.getLocation().getCountry());
            descriptionText.setText(accommodation.getDescription());

            weekendText.setText(String.valueOf(accommodation.getWeekendPrice()));
            holidayText.setText(String.valueOf(accommodation.getHolidayPrice()));
            summerText.setText(String.valueOf(accommodation.getSummerPrice()));
            limitText.setText(String.valueOf(accommodation.getCancelDeadline()));

            if (!accommodation.isNight()) {
                guestRadioButton.setChecked(true);
            } else {
                nightRadioButton.setChecked(true);
            }

            if (accommodation.isAutomaticConfirmation()) {
                automaticRadioButton.setChecked(true);
            } else {
                noAutomaticRadioButton.setChecked(true);
            }
        }

        Button addPriceDatebutton=findViewById(R.id.addPriceDateDefinition);

        Button editDefinitionbutton=findViewById(R.id.editAccommodationDefinition);

        List<Price> priceList=accommodation.getPrices();


        TableLayout tableLayout = findViewById(R.id.tableDefinition);

        Price AddPrice = new Price(100L, 0, new Date(), new Date());

        CalendarView calendarView1 = findViewById(R.id.calendarView1);

        CalendarView calendarView2 = findViewById(R.id.calendarView2);
        calendarView1.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                // Kreiranje Calendar instance sa izabranim datumom
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);
                Date selectedDate = calendar.getTime();
                AddPrice.setStartDate(selectedDate);
            }
        });


        calendarView2.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                // Kreiranje Calendar instance sa izabranim datumom
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);
                Date selectedDate = calendar.getTime();
                AddPrice.setEndDate(selectedDate);
            }
        });

        for (Price price : priceList) {

            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

            tableRow.setTag(price.getId());

            TextView startDateView = new TextView(this);
            startDateView.setText(DateFormat.format("dd-MM-yyyy", price.getStartDate()));
            startDateView.setPadding(4, 4, 4, 4);

            TextView endDateView = new TextView(this);
            endDateView.setText(DateFormat.format("dd-MM-yyyy", price.getEndDate()));
            endDateView.setPadding(4, 4, 4, 4);

            TextView priceView = new TextView(this);
            priceView.setText(String.valueOf(price.getPrice()));
            priceView.setPadding(4, 4, 4, 4);

            // Dodavanje Button za brisanje
            Button deleteButton = new Button(this);
            deleteButton.setText("Delete");
            deleteButton.setPadding(4, 4, 4, 4);
            deleteButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Log.d("DELETE","DUGME DELETE");
                    View row = tableLayout.findViewWithTag(price.getId());
                    if (row != null) {
                        tableLayout.removeView(row);
                        priceList.remove(row.getTag());
                        Long priceIdToRemove = (Long) row.getTag();
                        Log.d("ID OBRISAN",priceIdToRemove.toString());
                        priceList.removeIf(price -> priceIdToRemove.equals(price.getId()));
                    }
                }
            });

            tableRow.addView(startDateView);
            tableRow.addView(endDateView);
            tableRow.addView(priceView);
            tableRow.addView(deleteButton);

            // Dodavanje reda u TableLayout
            tableLayout.addView(tableRow);
        }

        addPriceDatebutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                EditText priceText = findViewById(R.id.priceuEditTextDefinition);
                double cena=Double.parseDouble(priceText.getText().toString());
                AddPrice.setPrice(cena);
                Price price=AddPrice;
                priceList.add(price);
                TableLayout tableLayout = findViewById(R.id.tableDefinition);

                TableRow tableRow = new TableRow(DefinitionAccommodationActivity.this);
                tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

                tableRow.setTag(price.getId());

                // Dodavanje TextView za datum početka
                TextView startDateView = new TextView(DefinitionAccommodationActivity.this);
                startDateView.setText(DateFormat.format("dd-MM-yyyy", price.getStartDate()));
                startDateView.setPadding(4, 4, 4, 4);

                TextView endDateView = new TextView(DefinitionAccommodationActivity.this);
                endDateView.setText(DateFormat.format("dd-MM-yyyy", price.getEndDate()));
                endDateView.setPadding(4, 4, 4, 4);

                TextView priceView = new TextView(DefinitionAccommodationActivity.this);
                priceView.setText(String.valueOf(price.getPrice()));
                priceView.setPadding(4, 4, 4, 4);

                Button deleteButton = new Button(DefinitionAccommodationActivity.this);
                deleteButton.setText("Delete");
                deleteButton.setPadding(4, 4, 4, 4);
                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TableRow parentRow = (TableRow) v.getParent();
                        tableLayout.removeView(parentRow);
                        priceList.remove(parentRow.getTag());
                        Long priceIdToRemove = (Long) parentRow.getTag();
                        Log.d("ID OBRISAN",priceIdToRemove.toString());
                        priceList.removeIf(price -> priceIdToRemove.equals(price.getId()));
                    }
                });

                tableRow.addView(startDateView);
                tableRow.addView(endDateView);
                tableRow.addView(priceView);
                tableRow.addView(deleteButton);

                tableLayout.addView(tableRow);
            }
        });

        editDefinitionbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //validacija sa datumima i rezervacijama
                EditText weekendText = findViewById(R.id.WeekendText);
                accommodation.setWeekendPrice(Double.parseDouble(weekendText.getText().toString()));
                EditText holidayText = findViewById(R.id.HolidayText);
                accommodation.setHolidayPrice(Double.parseDouble(holidayText.getText().toString()));
                EditText summerText = findViewById(R.id.SummerText);
                accommodation.setSummerPrice(Double.parseDouble(summerText.getText().toString()));

                //accommodation.setWeekendPrice(Double.parseDouble(weekendText.getText().toString()));

                String name = getValueById(nameText);
                String minPeople = getValueById(minPeopleText);
                String maxPeople = getValueById(maxPeopleText);
                String country = getValueById(countryText);
                String city = getValueById(cityText);
                String street = getValueById(streetText).split(" ")[0];
                String number = getValueById(streetText).split(" ")[1];
                String amenity = getValueById(amenityText);

                if (name == null || minPeople == null || maxPeople == null || country == null
                        || city == null || street == null) {
                    wrongInput();
                    return;
                }

                String[] amenityArray = amenity.split(", ");
                List<Amenity> amenities = new ArrayList<>();
                for (String amenityName : amenityArray) {
                    Amenity amenityObject = new Amenity(amenityName);
                    amenities.add(amenityObject);
                }



                RadioGroup typeRadioGroup1 = findViewById(R.id.RadioGroupDefinition1);
                RadioButton guestRadioButton = findViewById(R.id.guestRadioButtonDefinition);
                RadioButton nightRadioButton = findViewById(R.id.nightRadioButtonDefinition);

                int selectedRadioButtonId = typeRadioGroup1.getCheckedRadioButtonId();

                if (selectedRadioButtonId == guestRadioButton.getId()) {
                    accommodation.setNight(false);
                } else {
                    accommodation.setNight(true);
                }

                RadioGroup typeRadioGroup2 = findViewById(R.id.RadioGroupDefinition2);
                RadioButton automaticRadioButton = findViewById(R.id.AutomaticConfirmationDefinition);
                RadioButton noAutomaticRadioButton = findViewById(R.id.NoAutomaticDefinition);

                int selectedRadioButtonId2 = typeRadioGroup2.getCheckedRadioButtonId();
                boolean isNight = false;
                if (selectedRadioButtonId2 == automaticRadioButton.getId()) {
                    accommodation.setAutomaticConfirmation(true);
                    isNight = true;
                } else {
                    accommodation.setAutomaticConfirmation(false);
                    isNight = false;
                }

                accommodation.setPrices(priceList);

                Call<Map<String, String>> call = ServiceUtils.accommodationService.updateAccommodationMobile(accommodation.getId(),Double.parseDouble(String.valueOf(weekendText.getText())),Double.parseDouble(String.valueOf(holidayText.getText())),Double.parseDouble(String.valueOf(summerText.getText())),isNight,24,
                name,Integer.parseInt(minPeople), Integer.parseInt(maxPeople), Integer.parseInt(number), street, city, country,  amenities);
                call.enqueue(new Callback<Map<String, String>>() {
                    @Override
                    public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                        Log.d("RESPONSE CODE AAA","A");
                        if (response.code() == 200){
                            Log.d("pogodi puT","POGODI PUT");
                            //staviti TOAST
                        }else{
                            Log.d("REZ","Meesage recieved: "+response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<Map<String, String>> call, Throwable t) {
                        Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
                    }
                });


            }
        });


    }

    private void wrongInput() {
        Toast.makeText(getBaseContext(), "Invalid inputs", Toast.LENGTH_SHORT);
    }

    private String getValueById(EditText editText) {
        return editText.getText().toString().trim();
    }
}