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
import com.example.shopapp.model.review.Report;
import com.example.shopapp.model.review.ReportAccommodation;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GeneralReportActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_report);
        Long idOwner = 1L;
        generateBarChart(idOwner);

        Button btnGeneratePDF = findViewById(R.id.btnGeneratePDF2);

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

    private void generateBarChart(Long idOwner){
        ArrayList<Report> reports = null;

        Call<ArrayList<Report>> call = ServiceUtils.ownerService.getOwnerReports(idOwner,null,null);
        call.enqueue(new Callback<ArrayList<Report>>() {
            @Override
            public void onResponse(Call<ArrayList<Report>> call, Response<ArrayList<Report>> response) {
                if (response.code() == 200){
                    ArrayList<Report> reports = response.body();

                    updateChart(reports);
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Report>> call, Throwable t) {
                Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
            }
        });
    }

    //imacemo 2 grafa, 1 za pare a 1 za br rezervacije
    private void updateChart(ArrayList<Report> reports) {

        BarChart barChart = findViewById(R.id.chartGeneralReport);
        BarChart barChart2 = findViewById(R.id.chartGeneralReport2);
        List<BarEntry> entries = new ArrayList<>();
        List<BarEntry> entries2 = new ArrayList<>();

        for (Report report:reports) {
            entries.add(new BarEntry(report.getAccommodation().getId(), report.getProfit()));
            entries2.add(new BarEntry(report.getAccommodation().getId(), report.getNumberReservation()));
        }

        BarDataSet dataSet = new BarDataSet(entries, "Label"); // Label možete promeniti u nešto relevantno za vaš set podataka
        //dataSet.setColors(Color.GREEN, Color.RED); // Set boja za svaki stubić, možete dodati više boja ili promeniti
        BarData barData = new BarData(dataSet);

        BarDataSet dataSet2 = new BarDataSet(entries2, "Label"); // Label možete promeniti u nešto relevantno za vaš set podataka
           BarData barData2 = new BarData(dataSet2);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setGranularity(1f); // minimalni interval između vrednosti je 1
        //xAxis.setValueFormatter(xAxisFormatter);

        XAxis xAxis2 = barChart2.getXAxis();
        xAxis2.setGranularity(1f);

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

}