package com.example.shopapp.model.user;

import java.io.Serializable;
import java.time.LocalDateTime;

public class User implements Serializable {
    private Long id;
    private String email;
    private String password;
    private String name;
    private String surname;
    private String phone;

    private String address;

    private boolean blocked;
    private boolean isReported;

    private boolean isActive;

    private String activationCode;

    private LocalDateTime activationExpiry;

    private String jwt;

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public User(Long id,String email, String password, String name, String surname, String phone, String address) {
        this.id=id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.address = address;
        this.blocked = false;
        this.isReported=false;
    }

    public User(Long id,String email, String password, String name, String surname, String phone, String address,boolean rep,boolean blocked) {
        this.id=id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.address = address;
        this.isReported=rep;
        this.blocked = blocked;
    }

    public User() {
    }

    public User(Long id) {
        this.id=id;
    }

    public User(User user){
        this.id=user.id;
        this.email = user.email;
        this.password = user.password;
        this.name = user.name;
        this.surname = user.surname;
        this.phone = user.phone;
        this.address = user.address;
        this.isReported=user.isReported;
        this.blocked = user.blocked;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public boolean isReported() {return isReported;}

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public void setReported(boolean reported) {
        this.isReported = reported;
    }
    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", blocked=" + blocked +
                '}';
    }

    public void copyValues(User user) {
        this.setEmail(user.getEmail());
        this.setPassword(user.getPassword());
        this.setName(user.getName());
        this.setSurname(user.getSurname());
        this.setPhone(user.getPhone());
        this.setAddress(user.getAddress());
        this.setReported(user.isReported());
        this.setBlocked(user.isBlocked());
    }
    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public LocalDateTime getActivationExpiry() {
        return activationExpiry;
    }

    public void setActivationExpiry(LocalDateTime activationExpiry) {
        this.activationExpiry = activationExpiry;
    }
}


