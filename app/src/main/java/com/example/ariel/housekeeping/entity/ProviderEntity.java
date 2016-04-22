package com.example.ariel.housekeeping.entity;

import java.io.Serializable;

/**
 * Created by ariel on 2016/4/20.
 */
public class ProviderEntity implements Serializable {
    private String name;
    private float rank;

    public ProviderEntity() {
    }

    public ProviderEntity(String name, float rank) {
        this.name = name;
        this.rank = rank;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public float getRank() {
        return rank;
    }
    public void setRank(float rank) {
        this.rank = rank;
    }

}

