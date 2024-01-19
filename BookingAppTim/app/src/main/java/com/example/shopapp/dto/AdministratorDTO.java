package com.example.shopapp.dto;

import android.os.Parcel;
import android.os.Parcelable;

public class AdministratorDTO implements Parcelable {
    private Long id;
    private String email;
    private String name;
    private String surname;

    public AdministratorDTO() {
    }


    protected AdministratorDTO(Parcel in) {
        id = in.readLong();
        email = in.readString();
        name = in.readString();
        surname = in.readString();
    }

    public static final Creator<AdministratorDTO> CREATOR = new Creator<AdministratorDTO>() {
        @Override
        public AdministratorDTO createFromParcel(Parcel in) {
            return new AdministratorDTO(in);
        }

        @Override
        public AdministratorDTO[] newArray(int size) {
            return new AdministratorDTO[size];
        }
    };

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String toString() {
        return "AdministratorDTO{" +
                "email='" + email + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(email);
        dest.writeString(name);
        dest.writeString(surname);
    }
}
