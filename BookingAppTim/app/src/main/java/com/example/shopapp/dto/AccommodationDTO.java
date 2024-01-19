package com.example.shopapp.dto;


import com.example.shopapp.enums.AccommodationStatus;
import com.example.shopapp.enums.TypeAccommodation;
import com.example.shopapp.model.accommodation.Accommodation;
import com.example.shopapp.model.accommodation.Amenity;
import com.example.shopapp.model.accommodation.Location;
import com.example.shopapp.model.accommodation.Price;
import com.example.shopapp.model.accommodation.TakenDate;
import com.example.shopapp.model.reservation.Reservation;
import com.example.shopapp.model.user.Owner;

import java.util.List;

public class AccommodationDTO {
    private  boolean accepted;
    private List<TakenDate> takenDates;
    private boolean automaticActivation = false;
    private Long id;
    private String name;
    private String description;
    private int minPeople;
    private int maxPeople;
    private List<String> photos;
    private TypeAccommodation typeAccomodation;
    private double rating;
    private int cancelDeadline;
    private boolean isAccepted;
    private Location location;
    private AccommodationStatus accommodationStatus;
    private Owner owner;
    private boolean automaticConfirmation;
    private List<Price> prices;


    private double weekendPrice;

    private double holidayPrice;
    private List<Amenity> amenities;
    private List<Reservation> reservations;

    private double summerPrice;

    private boolean isNight;

    public Owner getOwner() {
        return owner;
    }

    public boolean isAutomaticConfirmation() {
        return automaticConfirmation;
    }

    public void setAutomaticConfirmation(boolean automaticConfirmation) {
        this.automaticConfirmation = automaticConfirmation;
    }

    public List<TakenDate> getTakenDates() {
        return takenDates;
    }

    public void setTakenDates(List<TakenDate> takenDates) {
        this.takenDates = takenDates;
    }

    public boolean isAutomaticActivation() {
        return automaticActivation;
    }

    public void setAutomaticActivation(boolean automaticActivation) {
        this.automaticActivation = automaticActivation;
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

    public List<Amenity> getAmenities() {
        return amenities;
    }

    public void setAmenities(List<Amenity> amenities) {
        this.amenities = amenities;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public double getSummerPrice() {
        return summerPrice;
    }

    public void setSummerPrice(double summerPrice) {
        this.summerPrice = summerPrice;
    }

    public boolean isNight() {
        return isNight;
    }

    public void setNight(boolean night) {
        isNight = night;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public AccommodationStatus getAccommodationStatus() {
        return accommodationStatus;
    }

    public void setAccommodationStatus(AccommodationStatus accommodationStatus) {
        this.accommodationStatus = accommodationStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public TypeAccommodation getTypeAccomodation() {
        return typeAccomodation;
    }

    public void setTypeAccomodation(TypeAccommodation typeAccommodation) {
        this.typeAccomodation = typeAccommodation;
    }

    public double getRating() {
        return rating;
    }

    public boolean isAccepted() {
        return isAccepted;
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

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public AccommodationDTO(){

    }

    public AccommodationDTO(Long id, String description, int minPeople, int maxPeople, List<String> photos, TypeAccommodation typeAccomodation, double rating, int cancelDeadline, boolean isAccepted,String name, AccommodationStatus status, Owner owner, boolean automaticConfirmation,List<Amenity> amenities) {

        this.id = id;
        this.description = description;
        this.minPeople = minPeople;
        this.maxPeople = maxPeople;
        this.photos = photos;
        this.typeAccomodation = typeAccomodation;
        this.rating = rating;
        this.cancelDeadline = cancelDeadline;
        this.isAccepted=isAccepted;
        this.accommodationStatus = status;
        this.owner = owner;
        this.automaticConfirmation = automaticConfirmation;
        this.name = name;
        this.amenities = amenities;
    }
    public AccommodationDTO(Long id, boolean accepted, boolean automaticActivation, String description, int minPeople, int maxPeople, String name, List<String> photos, TypeAccommodation typeAccomodation, double rating, int cancelDeadline, List<Price> prices, List<TakenDate> takenDates, List<Amenity> amenities, Location location, Owner owner, List<Reservation> reservations, double weekendPrice, double holidayPrice, double summerPrice, boolean isNight, AccommodationStatus status) {

        this.id = id;
        this.accepted = accepted;
        this.automaticActivation = automaticActivation;
        this.description = description;
        this.minPeople = minPeople;
        this.maxPeople = maxPeople;
        this.photos = photos;
        this.typeAccomodation = typeAccomodation;
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

    public AccommodationDTO(Long id, String description, int minPeople, int maxPeople, List<String> photo, TypeAccommodation typeAccomodation, double rating, int cancelDeadline, boolean isAccepted,Location location,String name, AccommodationStatus status, Owner owner, boolean automaticConfirmation,List<Amenity> amenities) {
        this.id = id;
        this.description = description;
        this.minPeople = minPeople;
        this.maxPeople = maxPeople;
        this.photos = photo;
        this.typeAccomodation = typeAccomodation;
        this.rating = rating;
        this.cancelDeadline = cancelDeadline;
        this.isAccepted=isAccepted;
        this.location = location;
        this.accommodationStatus = status;
        this.owner = owner;
        this.automaticConfirmation = automaticConfirmation;
        this.name = name;
        this.amenities = amenities;
    }

    public AccommodationDTO(Accommodation a) {
        this();
        this.id = a.getId();
        this.description = a.getDescription();
        this.minPeople = a.getMinPeople();
        this.maxPeople = a.getMaxPeople();
        this.photos = a.getPhotos();
        this.typeAccomodation = a.getTypeAccommodation();
        this.rating = a.getRating();
        this.cancelDeadline = a.getCancelDeadline();
        this.isAccepted = a.isAccepted();
        this.location = a.getLocation();
        this.accommodationStatus = a.getAccommodationStatus();
        this.owner = a.getOwner();
        this.automaticConfirmation = a.isAutomaticConfirmation();
        this.name = a.getName();
        this.weekendPrice = a.getWeekendPrice();
        this.summerPrice = a.getSummerPrice();
        this.holidayPrice = a.getHolidayPrice();
        this.cancelDeadline = a.getCancelDeadline();
        this.prices = a.getPrices();
        this.amenities = a.getAmenities();
    }

    @Override
    public String toString() {
        return "AccommodationDTO{" +
                "accepted=" + accepted +
                ", takenDates=" + takenDates +
                ", automaticActivation=" + automaticActivation +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", minPeople=" + minPeople +
                ", maxPeople=" + maxPeople +
                ", photos=" + photos +
                ", typeAccomodation=" + typeAccomodation +
                ", rating=" + rating +
                ", cancelDeadline=" + cancelDeadline +
                ", isAccepted=" + isAccepted +
                ", location=" + location +
                ", accommodationStatus=" + accommodationStatus +
                ", owner=" + owner +
                ", automaticConfirmation=" + automaticConfirmation +
                ", prices=" + prices +
                ", weekendPrice=" + weekendPrice +
                ", holidayPrice=" + holidayPrice +
                ", amenities=" + amenities +
                ", reservations=" + reservations +
                ", summerPrice=" + summerPrice +
                ", isNight=" + isNight +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }


}
