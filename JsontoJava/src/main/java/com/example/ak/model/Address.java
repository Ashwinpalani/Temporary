package com.example.ak.model;

public class Address {
	private long door_no;
	private String village;
	private String district;
	private long pincode;
	
	public long getDoor_no() {
		return door_no;
	}
	public void setDoor_no(long door_no) {
		this.door_no = door_no;
	}
	public String getVillage() {
		return village;
	}
	public void setVillage(String village) {
		this.village = village;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public long getPincode() {
		return pincode;
	}
	public void setPincode(long pincode) {
		this.pincode = pincode;
	}
	

	public String toString()
	{
		return getDoor_no()+","+getVillage()+","+getDistrict()+","+getPincode();
	}
}
