package com.ta.weather.Controller;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ta.weather.Model.Response_Out;
import com.ta.weather.Service.WeatherService;

import lombok.extern.log4j.Log4j2;
@Log4j2
@RestController
@RequestMapping
public class ApiController {
	@Autowired
	private WeatherService weatherService;


	@GetMapping(value ="/report",produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Response_Out> fetchWeatherResponse(HttpServletRequest request) {
		List<Response_Out> weatherDataList  = new ArrayList<Response_Out>();
		try {
			weatherDataList =  weatherService.fetchWeatherList(request);
	}catch (Exception ex) {
		log.error("stack trace : " + Arrays.toString(ex.getStackTrace()));
        throw new RuntimeException(ex.getMessage());
    }
		 return weatherDataList;
	}
	
	@GetMapping(path = "weatherBetween/{endDate}/{startDate}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Response_Out> getWeatherDataBetween(HttpServletRequest request,
	   @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate, 
	    @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate)  {
		 List<Response_Out> responseOutList = new ArrayList<Response_Out>();
		try {
			responseOutList = weatherService.getWeatherDataBetween(startDate, endDate,request);
		}catch (Exception ex) {
			log.error("stack trace : " + Arrays.toString(ex.getStackTrace()));
	        throw new RuntimeException(ex.getMessage());
	    }
	  return responseOutList;
	}

	
	@GetMapping(value = "weather/{field}/{page}/{size}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Response_Out> getReportByCount (@RequestParam String field, @RequestParam int page, @RequestParam int size) {
		 List<Response_Out> responseOutList = new ArrayList<Response_Out>();
		 try {
		responseOutList = weatherService.getReportByCount(page, size, field);
		 }catch (Exception ex) {
				log.error("Exceptions : " + Arrays.toString(ex.getStackTrace()));
		        throw new RuntimeException(ex.getMessage());
		    }
		return responseOutList;
	}
	
	
//	@GetMapping(value = "/weather", produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<Object> getWeatherData() {
//		ResponseEntity<Object> weatherData =null;
//		try {
//			weatherData = weatherService.getWeatherData();
//			log.info("Weather Data : "+weatherData);
//		}catch (Exception ex) {
//	    	
//	        throw new RuntimeException(ex.getMessage());
//	    }
//		return new ResponseEntity<Object>(weatherData,HttpStatus.OK);
//	}
}