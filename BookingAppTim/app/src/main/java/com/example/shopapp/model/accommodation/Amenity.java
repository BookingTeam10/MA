package com.example.shopapp.model.accommodation;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Amenity implements Parcelable {
    private String name;
    public Amenity() {}

    @Override
    public String toString() {
        return "Amenity{" +
                "name='" + name + '\'' +
                '}';
    }
    protected Amenity(Parcel in) {
        name = in.readString();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);

    }

    public static final Creator<Amenity> CREATOR = new Creator<Amenity>() {
        @Override
        public Amenity createFromParcel(Parcel in) {
            return new Amenity(in);
        }

        @Override
        public Amenity[] newArray(int size) {
            return new Amenity[size];
        }
    };
}
