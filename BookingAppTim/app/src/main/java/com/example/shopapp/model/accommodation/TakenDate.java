package com.example.shopapp.model.accommodation;
import java.util.Date;
public class TakenDate {
    private Date firstDate;
    private Date lastDate;
    public TakenDate() {

    }

    public TakenDate(Date firstDate, Date lastDate) {
        this.firstDate = firstDate;
        this.lastDate = lastDate;
    }

    @Override
    public String toString() {
        return "TakenDate{" +
                "firstDate=" + firstDate +
                ", lastDate=" + lastDate +
                '}';
    }
}
