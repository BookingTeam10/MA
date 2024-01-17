package com.example.shopapp.model.user;


import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.shopapp.dto.GuestDTO;
import com.example.shopapp.model.accommodation.Accommodation;

import java.util.ArrayList;
import java.util.List;

public class Administrator extends  User implements Parcelable {


    public Administrator(Long id, String email, String password, String name, String surname, String phone, String address) {
        super(id, email, password, name, surname, phone, address);
    }

    public Administrator(Administrator a) {
        super(a.getId(), a.getEmail(), a.getPassword(), a.getName(), a.getSurname(), a.getPhone(), a.getAddress(), a.isReported(), a.isBlocked());
    }

    public Administrator() {
    }


    @Override
    public String toString() {
        return "AndroidAdministrator{" +
                "id=" + super.getId() +
                ", email='" + super.getEmail() + '\'' +
                ", password='" + super.getPassword() + '\'' +
                '}';
    }

    public void copyValues(Administrator administrator) {
        this.setEmail(administrator.getEmail());
        this.setPassword(administrator.getPassword());
    }



//    public static final Creator<Administrator> CREATOR = new Creator<Administrator>() {
//        @Override
//        public Administrator createFromParcel(Parcel source) {
//            return null;
//        }
//
//        @Override
//        public Administrator[] newArray(int size) {
//            return new Administrator[0];
//        }
//
//        @Override
//        public int describeContents() {
//            return 0;
//        }
//    };
//
//    private Administrator(Parcel in) {
//
//    }

    protected Administrator(Parcel in) {
        this.setEmail(in.readString());
        this.setName(in.readString());
        this.setSurname(in.readString());
    }

        public final Creator<Administrator> CREATOR = new Creator<Administrator>() {
        @Override
        public Administrator createFromParcel(Parcel in) {

            return new Administrator(in);
        }

        @Override
        public Administrator[] newArray(int size) {
            return new Administrator[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.getName());
        dest.writeString(this.getSurname());
        dest.writeString(this.getEmail());
    }
}
