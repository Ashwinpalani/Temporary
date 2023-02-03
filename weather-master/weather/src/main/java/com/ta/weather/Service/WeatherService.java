package com.ta.weather.Service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import com.ta.weather.Dao.ResponseDao;
import com.ta.weather.Model.Response_Out;
@Component
public class WeatherService{
	
	@Autowired
	private ResponseDao responseDao;
	
	public ResponseEntity<Object> getWeatherData() {
		return responseDao.getWeatherData();
	}

	public List<Response_Out> fetchWeatherList(HttpServletRequest request) {
		return responseDao.fetchWeatherList(request);
	}

	public List<Response_Out> getWeatherDataBetween(Date startDate, Date endDate,HttpServletRequest request) {
		return responseDao.getWeatherDataBetween(startDate, endDate,request);
	}

	public List<Response_Out> getReportByCount(int page, int size, String field) {
		return responseDao.getReportByCount(page, size, field);
	}
}
	