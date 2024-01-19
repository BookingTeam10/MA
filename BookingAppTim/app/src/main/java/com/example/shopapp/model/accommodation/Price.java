package com.example.shopapp.model.accommodation;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Date;
public class Price implements Parcelable {
    private Long id;
    private double price;
    private Date startDate;
    private Date endDate;

    public Price() {
    }
    public Price(Long id, double price, Date startDate, Date endDate) {
        this.id = id;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    protected Price(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        price = in.readDouble();
        startDate = new Date(in.readLong());
        endDate = new Date(in.readLong());
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        dest.writeDouble(price);
        dest.writeLong(startDate.getTime());
        dest.writeLong(endDate.getTime());

    }

    public static final Creator<Price> CREATOR = new Creator<Price>() {
        @Override
        public Price createFromParcel(Parcel in) {
            return new Price(in);
        }

        @Override
        public Price[] newArray(int size) {
            return new Price[size];
        }
    };

    @Override
    public String toString() {
        return "Price{" +
                "id=" + id +
                ", price=" + price +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}