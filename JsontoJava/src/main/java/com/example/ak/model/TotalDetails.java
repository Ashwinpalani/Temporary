package com.example.ak.model;

import java.util.List;

public class TotalDetails {
	
	List<EmployeeDetails> ed;

	public List<EmployeeDetails> getEd() {
		return ed;
	}

	public void setEd(List<EmployeeDetails> ed) {
		this.ed = ed;
	}

	@Override
	public String toString() {
		return "TotalDetails [ed=" + ed + "]";
	}

	
}
