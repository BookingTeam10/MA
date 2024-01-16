package com.example.shopapp.model.accommodation;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.shopapp.dto.AccommodationDTO;
import com.example.shopapp.enums.AccommodationStatus;
import com.example.shopapp.enums.TypeAccommodation;
import com.example.shopapp.model.reservation.Reservation;
import com.example.shopapp.model.user.Owner;

import java.util.ArrayList;
import java.util.List;

public class Accommodation implements Parcelable {
    private Long id;
    private String name;
    private String description;
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
    public int image;
    public Accommodation(Long id, String name, String description, int image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
    }

    public Accommodation() {
    }

    public Accommodation(AccommodationDTO accommodation) {
        this.accepted=accommodation.isAccepted();
        this.takenDates=accommodation.getTakenDates();
        this.automaticActivation=accommodation.isAutomaticActivation();
        this.id=accommodation.getId();
        this.name=accommodation.getName();
        this.description=accommodation.getDescription();
        this.minPeople=accommodation.getMinPeople();
        this.maxPeople=accommodation.getMaxPeople();
        this.photos=accommodation.getPhotos();
        this.typeAccommodation=accommodation.getTypeAccomodation();
        this.rating=accommodation.getRating();
        this.cancelDeadline=accommodation.getCancelDeadline();
        this.location=accommodation.getLocation();
        this.accommodationStatus=accommodation.getAccommodationStatus();
        this.owner=accommodation.getOwner();
        this.automaticConfirmation=accommodation.isAutomaticConfirmation();
        this.prices=accommodation.getPrices();
        this.weekendPrice=accommodation.getWeekendPrice();
        this.holidayPrice=accommodation.getHolidayPrice();
        this.amenities=accommodation.getAmenities();
        this.reservations=accommodation.getReservations();
        this.summerPrice=accommodation.getSummerPrice();
        this.isNight=accommodation.isNight();
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public boolean isAutomaticActivation() {
        return automaticActivation;
    }

    public void setAutomaticActivation(boolean automaticActivation) {
        this.automaticActivation = automaticActivation;
    }

    public int getMinPeople() {
        return minPeople;
    }

    public void setMinPeople(int minPeople) {
        this.minPeople = minPeople;
    }

    public int getMaxPeople() {
        return maxPeople;
    }

    public void setMaxPeople(int maxPeople) {
        this.maxPeople = maxPeople;
    }

    public TypeAccommodation getTypeAccommodation() {
        return typeAccommodation;
    }

    public void setTypeAccommodation(TypeAccommodation typeAccommodation) {
        this.typeAccommodation = typeAccommodation;
    }

    public AccommodationStatus getAccommodationStatus() {
        return accommodationStatus;
    }

    public void setAccommodationStatus(AccommodationStatus accommodationStatus) {
        this.accommodationStatus = accommodationStatus;
    }

    public boolean isNight() {
        return isNight;
    }

    public void setNight(boolean night) {
        isNight = night;
    }

    public List<TakenDate> getTakenDates() {
        return takenDates;
    }

    public void setTakenDates(List<TakenDate> takenDates) {
        this.takenDates = takenDates;
    }

    public List<Amenity> getAmenities() {
        return amenities;
    }

    public void setAmenities(List<Amenity> amenities) {
        this.amenities = amenities;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public boolean isAutomaticConfirmation() {
        return automaticConfirmation;
    }

    public void setAutomaticConfirmation(boolean automaticConfirmation) {
        this.automaticConfirmation = automaticConfirmation;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getCancelDeadline() {
        return cancelDeadline;
    }

    public void setCancelDeadline(int cancelDeadline) {
        this.cancelDeadline = cancelDeadline;
    }

    public List<Price> getPrices() {
        return prices;
    }

    public void setPrices(List<Price> prices) {
        this.prices = prices;
    }

    public double getWeekendPrice() {
        return weekendPrice;
    }

    public void setWeekendPrice(double weekendPrice) {
        this.weekendPrice = weekendPrice;
    }

    public double getHolidayPrice() {
        return holidayPrice;
    }

    public void setHolidayPrice(double holidayPrice) {
        this.holidayPrice = holidayPrice;
    }

    public double getSummerPrice() {
        return summerPrice;
    }

    public void setSummerPrice(double summerPrice) {
        this.summerPrice = summerPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return   name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeInt(image);
        dest.writeTypedList(amenities);
        dest.writeTypedList(prices);
        if (Build.VERSION.SDK_INT >= 29) {
            dest.writeBoolean(automaticConfirmation);
        }
    }

    protected Accommodation(Parcel in) {
        id = in.readLong();
        name = in.readString();
        description = in.readString();
        image = in.readInt();
        amenities = new ArrayList<>();
        in.readTypedList(amenities, Amenity.CREATOR);
        prices = new ArrayList<>();
        in.readTypedList(prices, Price.CREATOR);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            automaticConfirmation = in.readBoolean();
        }
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
    public Accommodation(String name){
        this.name = name;
    }

    public Accommodation(Long id, boolean accepted, boolean automaticActivation, String description, int minPeople, int maxPeople,String name, List<String> photos, TypeAccommodation typeAccommodation, double rating, int cancelDeadline, List<Price> prices, List<TakenDate> takenDates, List<Amenity> amenities, Location location, Owner owner, List<Reservation> reservations,double weekendPrice, double holidayPrice, double summerPrice,boolean isNight, AccommodationStatus status) {

        this.id = id;
        this.accepted = accepted;
        this.automaticActivation = automaticActivation;
        this.description = description;
        this.minPeople = minPeople;
        this.maxPeople = maxPeople;
        this.photos = photos;
        this.typeAccommodation = typeAccommodation;
        this.rating = rating;
        this.cancelDeadline = cancelDeadline;
        this.prices = prices;
        this.takenDates = takenDates;
        this.amenities = amenities;
        this.location = location;
        this.owner = owner;
        this.reservations = reservations;
        this.accommodationStatus = status;
        this.automaticConfirmation = automaticActivation;
        this.weekendPrice=weekendPrice;
        this.holidayPrice=holidayPrice;
        this.summerPrice=summerPrice;
        this.isNight=isNight;
        this.name = name;
    }
}