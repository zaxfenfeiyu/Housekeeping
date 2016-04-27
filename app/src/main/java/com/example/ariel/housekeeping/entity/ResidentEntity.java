package com.example.ariel.housekeeping.entity;

public class ResidentEntity {
	private int id;
	private String account;
	private String realname;
	private String address;
	private String phone;

	public ResidentEntity(int id, String account, String realname, String address, String phone) {
		this.id = id;
		this.account = account;
		this.realname = realname;
		this.address = address;
		this.phone = phone;
	}
	public ResidentEntity() {

	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

}
