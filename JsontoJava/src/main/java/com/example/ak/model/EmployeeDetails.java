package com.example.ak.model;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeeDetails {

	@JsonProperty("emp_id")
	private int emp_id;

	@JsonProperty("name")
	private String name;

	@JsonProperty("position")
	private String position;

	@JsonProperty("address")
	private Address address;

	@JsonProperty("role")
	private List<String> role;

	@JsonProperty("salary")
	private Map<String, Integer> salary;

	public int getEmp_id() {
		return emp_id;
	}

	public void setEmp_id(int emp_id) {
		this.emp_id = emp_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public List<String> getRole() {
		return role;
	}

	public void setRole(List<String> role) {
		this.role = role;
	}

	public Map<String, Integer> getSalary() {
		return salary;
	}

	public void setSalary(Map<String, Integer> salary) {
		this.salary = salary;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("****Employee Details****\n");
		sb.append("ID= " + getEmp_id() + "\n");
		sb.append("NAME= " + getName() + "\n");
		sb.append("POSITION= " + getPosition() + "\n");
		sb.append("Address= " + getAddress() + "\n");
		sb.append("All_Roles= " + getRole() + "\n");
		sb.append("SalaryForRoles= " + getSalary() + "\n");
		sb.append("-----------------------------------------------");

		return sb.toString();
	}

}
