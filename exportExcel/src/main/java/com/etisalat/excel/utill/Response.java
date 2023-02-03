package com.etisalat.excel.utill;

import java.util.List;

public class Response<T> {
	private Integer statusCode;
	private String message;

	public List<ExcelVO> response;
	
	public Response(Integer statusCode, String message) {
		this.statusCode = statusCode;
		this.message = message;
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<ExcelVO> getResponse() {
		return response;
	}

	public Response<T> setResponse(List<ExcelVO> list) {
		this.response = list;
		return this;
	}

}
