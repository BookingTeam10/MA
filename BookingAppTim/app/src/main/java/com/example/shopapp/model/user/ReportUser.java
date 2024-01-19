package com.example.shopapp.model.user;


import com.example.shopapp.enums.ReviewStatus;

import java.io.Serializable;

public class ReportUser implements Serializable {
    private Long id;


    private String comment;

    private ReviewStatus status;

    private Owner owner;

    private Guest guest;

    private String userReportUser;

    public ReportUser() {
    }

    public ReportUser(Long id, String comment, ReviewStatus status, Owner owner, Guest guest, String userReportUser) {
        this.id = id;
        this.comment = comment;
        this.status = status;
        this.owner = owner;
        this.guest = guest;
        this.userReportUser = userReportUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public ReviewStatus getStatus() {
        return status;
    }

    public void setStatus(ReviewStatus status) {
        this.status = status;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public String getUserReportUser() {
        return userReportUser;
    }

    public void setUserReportUser(String userReportUser) {
        this.userReportUser = userReportUser;
    }

    @Override
    public String toString() {
        return "ReportUser{" +
                "id=" + id +
                ", comment='" + comment + '\'' +
                ", status=" + status +
                ", owner=" + owner +
                ", guest=" + guest +
                ", userReportUser='" + userReportUser + '\'' +
                '}';
    }
}
