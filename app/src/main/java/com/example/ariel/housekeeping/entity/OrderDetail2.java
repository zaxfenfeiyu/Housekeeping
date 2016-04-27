package com.example.ariel.housekeeping.entity;

import java.util.Date;

public class OrderDetail2 {

	private int id;
	private String pro_name;
	private String stsc_name;
	private double price;
	private String re_name;
	private String re_phone;
	private String service_time;
	private String place_time;
	private String address;
	private String message;

	public OrderDetail2() {
	}

	public OrderDetail2(int id, String pro_name, String stsc_name, double price, String re_name, String re_phone, String service_time, String place_time, String address, String message) {
		this.id = id;
		this.pro_name = pro_name;
		this.stsc_name = stsc_name;
		this.price = price;
		this.re_name = re_name;
		this.re_phone = re_phone;
		this.service_time = service_time;
		this.place_time = place_time;
		this.address = address;
		this.message = message;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPro_name() {
		return pro_name;
	}
	public void setPro_name(String pro_name) {
		this.pro_name = pro_name;
	}
	public String getStsc_name() {
		return stsc_name;
	}
	public void setStsc_name(String stsc_name) {
		this.stsc_name = stsc_name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getRe_name() {
		return re_name;
	}
	public void setRe_name(String re_name) {
		this.re_name = re_name;
	}
	public String getRe_phone() {
		return re_phone;
	}
	public void setRe_phone(String re_phone) {
		this.re_phone = re_phone;
	}
	public String getService_time() {
		return service_time;
	}
	public void setService_time(String service_time) {
		this.service_time = service_time;
	}

	public String getPlace_time() {
		return place_time;
	}

	public void setPlace_time(String place_time) {
		this.place_time = place_time;
	}

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
