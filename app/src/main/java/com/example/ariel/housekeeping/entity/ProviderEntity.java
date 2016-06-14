package com.example.ariel.housekeeping.entity;

import java.io.Serializable;

/**
 * Created by ariel on 2016/4/20.
 */
public class ProviderEntity implements Serializable {
    private int id;
    private String name;
    private double rank;
    private String phone;
    private String introduction;
    private String service;
    private String picturepath;

    public ProviderEntity() {
    }

    public ProviderEntity(int id, String name, double rank, String phone, String introduction, String service, String picturepath) {
        this.id = id;
        this.name = name;
        this.rank = rank;
        this.phone = phone;
        this.introduction = introduction;
        this.service = service;
        this.picturepath = picturepath;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getRank() {
        return rank;
    }
    public void setRank(double rank) {
        this.rank = rank;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getPicturepath() {
        return picturepath;
    }

    public void setPicturepath(String picturepath) {
        this.picturepath = picturepath;
    }
}

