package com.example.shopapp.model.accommodation;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Location  implements Parcelable{
    private Long id;
    private String country;
    private String city;
    private String street;
    private int number;
    private List<Accommodation> accommodations;

    public Location(){

    }

    public Location(Long id, String country, String city, String street, int number) {
        this.id = id;
        this.country = country;
        this.city = city;
        this.street = street;
        this.number = number;
    }

    protected Location(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        country = in.readString();
        city = in.readString();
        street = in.readString();
        number = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        dest.writeString(country);
        dest.writeString(city);
        dest.writeString(street);
        dest.writeInt(number);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Location> CREATOR = new Creator<Location>() {
        @Override
        public Location createFromParcel(Parcel in) {
            return new Location(in);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", number=" + number +
                ", accommodations=" + accommodations +
                '}';
    }
}