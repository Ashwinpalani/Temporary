package com.aswin.cruddemo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String village;
	private int pincode;

	public Address() {

	}

	public Address(long id, String village, int pincode) {
		this.id = id;
		this.village = village;
		this.pincode = pincode;
	}

	public long getid() {
		return id;
	}

	public void setid(long id) {
		this.id = id;
	}

	public String getVillage() {
		return village;
	}

	public void setVillage(String village) {
		this.village = village;
	}

	public int getPincode() {
		return pincode;
	}

	public void setPincode(int pincode) {
		this.pincode = pincode;
		this.pincode = pincode;
		this.pincode = pincode;
	}

	@Override
	public String toString() {
		return "Address [id=" + id + ", village=" + village + ", pincode=" + pincode + "]";
	}

}
