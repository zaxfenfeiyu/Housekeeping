package com.example.ariel.housekeeping.entity;

public class TotalOrder {

	private int id;
	private String pro_name;
	private String st_sc_name;
	private String state;
	
	
	public TotalOrder() {
	}


	public TotalOrder(int id, String pro_name, String st_sc_name, String state) {
		this.id = id;
		this.pro_name = pro_name;
		this.st_sc_name = st_sc_name;
		this.state = state;
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


	public String getSt_sc_name() {
		return st_sc_name;
	}


	public void setSt_sc_name(String st_sc_name) {
		this.st_sc_name = st_sc_name;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}
	
	
}
