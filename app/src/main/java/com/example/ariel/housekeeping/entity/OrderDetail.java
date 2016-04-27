package com.example.ariel.housekeeping.entity;

import java.io.Serializable;

/**
 * Created by ariel on 2016/4/26.
 */
public class OrderDetail implements Serializable {
    private int sc_id;
    private String address;
    private String time;
    private String message;
    private double price;

    public OrderDetail() {
    }

    public OrderDetail(int sc_id, String address, String time, String message, double price) {
        this.sc_id = sc_id;
        this.address = address;
        this.time = time;
        this.message = message;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getSc_id() {
        return sc_id;
    }

    public void setSc_id(int sc_id) {
        this.sc_id = sc_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
