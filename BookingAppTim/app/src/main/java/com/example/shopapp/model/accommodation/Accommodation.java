package com.example.shopapp.model.accommodation;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.shopapp.enums.AccommodationStatus;
import com.example.shopapp.enums.TypeAccommodation;
import com.example.shopapp.model.reservation.Reservation;
import com.example.shopapp.model.user.Owner;

import java.util.List;

public class Accommodation implements Parcelable {
    private Long id;
    private String title;
    private String description;
    private int image;
    private boolean accepted;
    private boolean automaticActivation = false;
    private int minPeople;
    private int maxPeople;
    private List<String> photos;
    private TypeAccommodation typeAccommodation;
    private AccommodationStatus accommodationStatus;
    private boolean isNight;
    private List<TakenDate> takenDates;
    private List<Amenity> amenities;
    private Location location;
    private Owner owner;
    private List<Reservation> reservations;
    private boolean automaticConfirmation;
    private double rating;
    private int cancelDeadline;
    private List<Price> prices;
    private double weekendPrice;
    private double holidayPrice;
    private double summerPrice;
    public Accommodation(Long id, String title, String description, int image) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.image = image;
    }

    public Accommodation() {
    }
    protected Accommodation(Parcel in) {
        id = in.readLong();
        title = in.readString();
        description = in.readString();
        image = in.readInt();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getImage() {
        return image;
    }
    public void setImage(int image) {
        this.image = image;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Product{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeInt(image);
    }

    public static final Creator<Accommodation> CREATOR = new Creator<Accommodation>() {
        @Override
        public Accommodation createFromParcel(Parcel in) {
            return new Accommodation(in);
        }

        @Override
        public Accommodation[] newArray(int size) {
            return new Accommodation[size];
        }
    };
}
