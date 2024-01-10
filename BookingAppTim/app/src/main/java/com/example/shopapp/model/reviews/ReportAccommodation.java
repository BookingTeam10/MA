package com.example.shopapp.model.reviews;


import com.example.shopapp.model.accommodation.Accommodation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

//za jedan smestaj da se prikaze profit i grafik za godinu dana
public class ReportAccommodation {
    Accommodation accommodation;
    HashMap<Integer,ArrayList<Integer>> map = new HashMap<>();

    public Accommodation getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
    }

    public ReportAccommodation() {
        LocalDate today = LocalDate.now();
        int month = today.getMonthValue();

        for(int i=0;i<12;i++){
            map.put(month, new ArrayList<>());
            month++;
            month = month%12;
        }

    }

    public ReportAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
        LocalDate today = LocalDate.now();
        int month = today.getMonthValue();
        ArrayList<Integer> list = new ArrayList<>();
        //prvi se odnosi na price a drugi na number reservation
        list.add(0);
        list.add(0);
        for(int i=0;i<12;i++){

            map.put(month, list);
            month++;
            month = month%12;
        }
    }

    public HashMap<Integer, ArrayList<Integer>> getMap() {
        return map;
    }

    public void setMap(HashMap<Integer, ArrayList<Integer>> map) {
        this.map = map;
    }


    @Override
    public String toString() {
        return "ReportAccommodation{" +
                "accommodation=" + accommodation +
                ", map=" + map +
                '}';
    }
}
