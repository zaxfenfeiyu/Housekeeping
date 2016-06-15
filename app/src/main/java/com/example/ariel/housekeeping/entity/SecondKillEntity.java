package com.example.ariel.housekeeping.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by ariel on 2016/6/15.
 */
public class SecondKillEntity implements Serializable {
    private int id;
    private int sc_id;
    private int pro_id;
    private String name;
    private int num;
    private Date time;

    public SecondKillEntity() {
    }

    public SecondKillEntity(int id, int sc_id, int pro_id, String name, int num, Date time) {
        this.id = id;
        this.sc_id = sc_id;
        this.pro_id = pro_id;
        this.name = name;
        this.num = num;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPro_id() {
        return pro_id;
    }

    public void setPro_id(int pro_id) {
        this.pro_id = pro_id;
    }

    public int getSc_id() {
        return sc_id;
    }

    public void setSc_id(int sc_id) {
        this.sc_id = sc_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
