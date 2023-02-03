package com.ta.weather.Model;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="external_log")
@Access(value=AccessType.FIELD)
public class Response_Out {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Integer id;
	private Date created_date;
	private String api_user;
	private String parameter;
	private String method;
	private String request_url;
	private String 	response_text;
	private Integer response_code;
	private String 	response_message;
	
	
	public Integer getId() {
		return id;
	}
	public Date getCreated_date() {
		return created_date;
	}
	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}
//	public void setCreated_date(String created_date) {
//		this.created_date = formatter.format(created_date);
//	}
	public String getApi_user() {
		return api_user;
	}
	public void setApi_user(String api_user) {
		this.api_user = api_user;
	}
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getRequest_url() {
		return request_url;
	}
	public void setRequest_url(String request_url) {
		this.request_url = request_url;
	}
	public String getResponse_text() {
		return response_text;
	}
	public void setResponse_text(String response_text) {
		this.response_text = response_text;
	}
	public Integer getResponse_code() {
		return response_code;
	}
	public void setResponse_code(Integer response_code) {
		this.response_code = response_code;
	}
	public String getResponse_message() {
		return response_message;
	}
	public void setResponse_message(String response_message) {
		this.response_message = response_message;
	}
	
	
}