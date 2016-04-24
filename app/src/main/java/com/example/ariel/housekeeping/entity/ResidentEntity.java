package com.example.ariel.housekeeping.entity;

public class ResidentEntity {
	private String account;
	private String realname;
	private String address;
	private String phone;
	public ResidentEntity(String account, String realname, String address, String phone) {
		
		this.account = account;
		this.realname = realname;
		this.address = address;
		this.phone = phone;
	}
	public ResidentEntity() {
		
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
