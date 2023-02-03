package com.etisalat.excel.utill;

public class ExcelVO {
	
	private String accountNumber ;
	private String srcTrxId;	
	private String trxDateTime;
	private String trxContent ;
	private String trxServiceDesc;	
	private String destAccountNumber;
	private String agent;
	
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getSrcTrxId() {
		return srcTrxId;
	}
	public void setSrcTrxId(String srcTrxId) {
		this.srcTrxId = srcTrxId;
	}
	public String getTrxDateTime() {
		return trxDateTime;
	}
	public void setTrxDateTime(String trxDateTime) {
		this.trxDateTime = trxDateTime;
	}
	public String getTrxContent() {
		return trxContent;
	}
	public void setTrxContent(String trxContent) {
		this.trxContent = trxContent;
	}
	public String getTrxServiceDesc() {
		return trxServiceDesc;
	}
	public void setTrxServiceDesc(String trxServiceDesc) {
		this.trxServiceDesc = trxServiceDesc;
	}
	public String getDestAccountNumber() {
		return destAccountNumber;
	}
	public void setDestAccountNumber(String destAccountNumber) {
		this.destAccountNumber = destAccountNumber;
	}
	public String getAgent() {
		return agent;
	}
	public void setAgent(String agent) {
		this.agent = agent;
	}
	
}
