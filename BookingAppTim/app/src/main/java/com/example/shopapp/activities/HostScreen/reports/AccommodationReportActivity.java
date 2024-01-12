package com.example.shopapp.activities.HostScreen.reports;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.shopapp.R;
import com.example.shopapp.configuration.ServiceUtils;
import com.example.shopapp.dto.GuestDTO;
import com.example.shopapp.model.accommodation.Accommodation;
import com.example.shopapp.model.review.ReportAccommodation;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
//import com.github.mikephil.charting.formatter.ValueFormatter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccommodationReportActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accommodation_report);
        Accommodation accommodation = getIntent().getParcelableExtra("accommodation");

        generateBarChart(accommodation);

        Button btnGeneratePDF = findViewById(R.id.btnGeneratePDF);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE);
            }
        }

        btnGeneratePDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generatePDF(v); // Poziv metode za generisanje PDF-a
            }
        });
    }

    public void generatePDF(View view) {
        PdfDocument pdfDocument = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(300, 600, 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);

        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        canvas.drawText("Testiranje generisanja PDF-a", 10, 25, paint);

        // Završite stranicu
        pdfDocument.finishPage(page);


        String fileName = "test.pdf";
        File file = new File(getFilesDir(), fileName); // Koristite interni direktorijum aplikacije

        try {
            pdfDocument.writeTo(new FileOutputStream(file));
            Toast.makeText(this, "PDF je generisan. Putanja: " + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.e("PDF Generation", "error " + e.toString());
            Toast.makeText(this, "Greška: " + e.toString(),  Toast.LENGTH_LONG).show();
        }

        // Zatvorite dokument
        pdfDocument.close();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Dozvola odobrena", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Dozvola odbijena", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void generateBarChart(Accommodation accommodation){


        ReportAccommodation reportAccommodation = null;

        Call<ReportAccommodation> call = ServiceUtils.ownerService.getOwnerReportYear(accommodation.getId());

        call.enqueue(new Callback<ReportAccommodation>() {
            @Override
            public void onResponse(Call<ReportAccommodation> call, Response<ReportAccommodation> response) {
                if (response.code() == 200){
                    System.out.println(response.body());
                    Log.i("REPORT ACCOMMODATION",response.body().toString());
                    ReportAccommodation reportAccommodation = response.body();
                    reportAccommodation.setAccommodation(accommodation);
                    updateChart(reportAccommodation);
                }
            }

            @Override
            public void onFailure(Call<ReportAccommodation> call, Throwable t) {
                Log.d("REZ123", t.getMessage() != null?t.getMessage():"error");
            }
        });
    }

    private void updateChart(ReportAccommodation reportAccommodation) {

        BarChart barChart = findViewById(R.id.barChart);
        BarChart barChart2 = findViewById(R.id.barChart2);
        HashMap<Integer, ArrayList<Integer>> map = reportAccommodation.getMap();
        List<BarEntry> entries = new ArrayList<>();
        List<BarEntry> entries2 = new ArrayList<>();
        //imacemo 2 grafa, 1 za pare a 1 za br rezervacije
        for (HashMap.Entry<Integer, ArrayList<Integer>> entry : map.entrySet()) {
            Integer key = entry.getKey();
            ArrayList<Integer> value = entry.getValue();
            // Ovde možete raditi nešto sa ključem (key) i vrednošću (value)
            System.out.println("Ključ: " + key + ", Vrednost: " + value);

            entries.add(new BarEntry(key, value.get(0)));
            entries2.add(new BarEntry(key, value.get(1)));
        }

        BarDataSet dataSet = new BarDataSet(entries, "Label"); // Label možete promeniti u nešto relevantno za vaš set podataka
        //dataSet.setColors(Color.GREEN, Color.RED); // Set boja za svaki stubić, možete dodati više boja ili promeniti
        BarData barData = new BarData(dataSet);

        BarDataSet dataSet2 = new BarDataSet(entries2, "Label"); // Label možete promeniti u nešto relevantno za vaš set podataka
        BarData barData2 = new BarData(dataSet2);

//        ValueFormatter xAxisFormatter = new ValueFormatter() {
//            @Override
//            public String getAxisLabel(float value, AxisBase axis) {
//                return "M" + ((int) value); // Ovo će označiti ose kao M1, M2, itd.
//            }
//        };

        XAxis xAxis = barChart.getXAxis();
        xAxis.setGranularity(1f); // minimalni interval između vrednosti je 1
        //xAxis.setValueFormatter(xAxisFormatter);

        XAxis xAxis2 = barChart2.getXAxis();
        xAxis2.setGranularity(1f);

        // Formatiranje Y ose, ako je potrebno
        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setLabelCount(8, true); // postavljanje broja labela na levoj Y osi
        barChart.getAxisRight().setEnabled(false); // isključivanje desne Y ose ako nije potrebna

        barChart.setData(barData);
        barChart.setFitBars(true); // prilagođavanje širine stubića
        barChart.getDescription().setEnabled(false); // isključivanje opisa grafikona
        barChart.animateY(1500); // animacija za grafikon

        YAxis leftAxis2 = barChart2.getAxisLeft();
        leftAxis2.setLabelCount(8, true); // postavljanje broja labela na levoj Y osi
        barChart2.getAxisRight().setEnabled(false); // isključivanje desne Y ose ako nije potrebna

        barChart2.setData(barData2);
        barChart2.setFitBars(true); // prilagođavanje širine stubića
        barChart2.getDescription().setEnabled(false); // isključivanje opisa grafikona
        barChart2.animateY(1500); // animacija za grafikon

        barChart.invalidate();
        barChart2.invalidate();
    }

}