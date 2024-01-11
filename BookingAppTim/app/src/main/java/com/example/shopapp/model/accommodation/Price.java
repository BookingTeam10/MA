package com.example.shopapp.model.accommodation;
import java.util.Date;
public class Price {
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

    @Override
    public String toString() {
        return "Price{" +
                "id=" + id +
                ", price=" + price +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
