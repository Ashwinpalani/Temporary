package com.Database.ser;

public class Employee {
	private int ID;
	private String FIRST_NAME;
	private int SALARY;
	
	public Employee() {
		
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getFIRST_NAME() {
		return FIRST_NAME;
	}
	public void setFIRST_NAME(String fIRST_NAME) {
		FIRST_NAME = fIRST_NAME;
	}
	public int getSALARY() {
		return SALARY;
	}
	public void setSALARY(int sALARY) {
		SALARY = sALARY;
	}
	public Employee(int id,String empname,int empsalary) {
		this.ID=id;
		this.FIRST_NAME=empname;
		this.SALARY=empsalary;
	}}

//	public int getID() {
//		return ID;
//	}
//	public void setID(int iD) {
//		this.ID = iD;
//		
//	}
//	public String getEMPNAME() {
//		return EMPNAME;
//	}
//	public void setEMPNAME(String eMPNAME) {
//		this.EMPNAME = eMPNAME;
//	}
//	public int getEMPSALARY() {
//		return EMPSALARY;
//	}
//	public void setEMPSALARY(int eMPSALARY) {
//		this.EMPSALARY = eMPSALARY;
//	}
//}
