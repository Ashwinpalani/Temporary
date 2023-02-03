package com.example.customer.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;



@Entity

//@NamedStoredProcedureQueries({
//	@NamedStoredProcedureQuery(name = "getAllEmployees",
//			procedureName = "get_Customer",
//			resultClasses = Customer.class)
//})
public class Customer {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	
	private int custNo;


	private String custName;


	private String country;
	
	
	public Customer() {
	}

	public Customer(int custNo, String custName, String country) {
		this.custNo = custNo;
		this.custName = custName;
		this.country = country;
	}

	public int getCustNo() {
		return custNo;
	}

	public void setCustNo(int custNo) {
		this.custNo = custNo;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getcountry() {
		return country;
	}

	public void setcountry(String country) {
		this.country = country;
	}
	
	public String toString()
	{
		return ""+ custNo +","+custName+","+country;
		
	}
}
