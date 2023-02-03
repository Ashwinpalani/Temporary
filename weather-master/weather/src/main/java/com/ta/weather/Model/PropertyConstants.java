package com.ta.weather.Model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
@Component
public class PropertyConstants {
	
	@Value("${spring.datasource.url}")
	private  String url;
	@Value("${spring.datasource.username}")
	private  String username;
	@Value("${password}")
	private  String password;
	@Value ("${spring.datasource.driver-class-name}")
	private  String driverClassName;
	@Value ("${appid}")
	private  String appKey;
	@Value ("${city}")
	private  String city;
	@Value ("${weatherEndPoint}")
	private  String weatherEndPoint;
	@Value ("${weatherMap}")
	private  String weatherMap;
	@Value ("${simplejob.frequency}")
	private  String frequency;
	@Value ("${cronExpression}")
	private  String  cronExpression;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDriverClassName() {
		return driverClassName;
	}
	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}
	public String getAppKey() {
		return appKey;
	}
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getWeatherEndPoint() {
		return weatherEndPoint;
	}
	public void setWeatherEndPoint(String weatherEndPoint) {
		this.weatherEndPoint = weatherEndPoint;
	}
	public String getWeatherMap() {
		return weatherMap;
	}
	public void setWeatherMap(String weatherMap) {
		this.weatherMap = weatherMap;
	}
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	
	
}

	

	