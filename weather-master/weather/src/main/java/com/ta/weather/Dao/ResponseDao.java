package com.ta.weather.Dao;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.ta.weather.Model.PropertyConstants;
import com.ta.weather.Model.ResponseInRepository;
import com.ta.weather.Model.ResponseOutRepository;
import com.ta.weather.Model.Response_In;
import com.ta.weather.Model.Response_Out;

@Component
public class ResponseDao {

	@Autowired
	Environment env;
	@Autowired
	public SessionFactory sessionFactory;
	@Autowired
	PropertyConstants propertyConstants;
	@Autowired
	private ResponseOutRepository responseOutRepository;
	@Autowired
	private ResponseInRepository responseInRepository;
	
	private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public ResponseEntity<Object> getWeatherData() {
		String weatherDetails = "";
		Response_Out responseOut = null;
		RestTemplate resttemplate = new RestTemplate();
		LocalDateTime localDateTime = null;
		String nameofCurrentMethod = "";
		String time = "";
		Date date = null;
		try {
			String url = UriComponentsBuilder.fromHttpUrl(propertyConstants.getWeatherEndPoint())
					.queryParam("q", propertyConstants.getCity())
					.queryParam("appid", propertyConstants.getAppKey()).build().toString();
			localDateTime = java.time.LocalDateTime.now();
			time = localDateTime.toString();
			time = time.replace("T", " ");
			date = formatter.parse(time);
			weatherDetails = resttemplate.getForObject(url, String.class);
			nameofCurrentMethod = new Exception().getStackTrace()[0].getMethodName();
			if (weatherDetails != null) {
				responseOut = new Response_Out();
				responseOut.setApi_user(env.getProperty("appid"));
				responseOut.setCreated_date(date);
				responseOut.setMethod(nameofCurrentMethod);
				responseOut.setParameter(env.getProperty("city"));
				responseOut.setRequest_url(url);
				responseOut.setResponse_text(weatherDetails);
				responseOut.setResponse_message(HttpStatus.OK.getReasonPhrase());
				responseOut.setResponse_code(HttpStatus.OK.value());
				responseOutRepository.save(responseOut);
			} else {
				return new ResponseEntity<Object>("Data Not Found ", HttpStatus.NOT_FOUND);
			}
		} catch (RestClientException ex) {
			throw new RestClientException(String.format(
					"ClientException:: Weather service Call Failure :: GET  service call failed with message %s", ex.getMessage()));

		} catch (Exception ex) {
			throw new RestClientException(String.format(
					"Exception:: Call Failure Exception", ex.getMessage()));
		}
		return new ResponseEntity<Object>(weatherDetails, HttpStatus.OK);
	}

	
	public String getWeatherMap() {
		String weatherMap = env.getProperty("weatherMap");
		return weatherMap;
	}

	
	@SuppressWarnings("unchecked")
	public List<Response_Out> fetchWeatherList(HttpServletRequest request) {
		 String nameofCurrentMethod = "";
		List<Response_Out> responseOutList = new ArrayList<Response_Out>();
		LocalDateTime localDateTime = null;
		Response_In responseIn = null;
		String time = "";
		try {
			nameofCurrentMethod = new Exception().getStackTrace()[0].getMethodName();
			responseOutList = responseOutRepository.findAll();
			localDateTime = java.time.LocalDateTime.now();
			time = localDateTime.toString();
			time = time.replace("T", " ");
			System.out.println(responseOutList);
			if (responseOutList != null) {
				responseIn = new Response_In();
				responseIn.setCreated_date(time);
				responseIn.setMethod(nameofCurrentMethod);
				responseIn.setParameter("");
				responseIn.setRequest_url(request.getRequestURI().toString());
				responseIn.setResponse_text(responseOutList.toString());
				responseIn.setResponse_message(HttpStatus.OK.getReasonPhrase());
				responseIn.setResponse_code(HttpStatus.OK.value());
				responseInRepository.save(responseIn);
			} else {
				return (List<Response_Out>) new ResponseEntity<String>("Data Not Found", HttpStatus.NOT_FOUND);
			}
		} catch (Exception ex) {

			throw new RuntimeException("Exception :"+Arrays.toString(ex.getStackTrace()));
		}
		return responseOutList;
	}

	public List<Response_Out> getWeatherDataBetween(Date startDate, Date endDate, HttpServletRequest request) {
		List<Response_Out> weatherData = null;
		CriteriaBuilder criteriaBuilder = sessionFactory.getCriteriaBuilder();
		LocalDateTime localDateTime = null;
		Response_In responseIn = null;
		String nameofCurrentMethod = "";
		String time = "";
		try {
			weatherData = new ArrayList<Response_Out>();
			nameofCurrentMethod = new Exception().getStackTrace()[0].getMethodName();

			CriteriaQuery<Response_Out> criteriaQuery_Response_Out = criteriaBuilder.createQuery(Response_Out.class);
			Root<Response_Out> response_Out = criteriaQuery_Response_Out.from(Response_Out.class);
			criteriaQuery_Response_Out.select(response_Out);
			criteriaQuery_Response_Out.where(criteriaBuilder.lessThan(response_Out.<Date>get("created_date"), endDate),
					criteriaBuilder
							.and(criteriaBuilder.greaterThan(response_Out.<Date>get("created_date"), startDate)));
			weatherData = sessionFactory.openSession().createQuery(criteriaQuery_Response_Out).getResultList();
			System.out.println("weatherData:" + weatherData);
			localDateTime = java.time.LocalDateTime.now();
			time = localDateTime.toString();
			time = time.replace("T", " ");
			if (weatherData != null) {
				responseIn = new Response_In();
				responseIn.setCreated_date(time);
				responseIn.setMethod(nameofCurrentMethod);
				responseIn.setParameter(endDate.toString());
				responseIn.setRequest_url(request.getRequestURI().toString());
				responseIn.setResponse_text(weatherData.toString());
				responseIn.setResponse_message(HttpStatus.OK.getReasonPhrase());
				responseIn.setResponse_code(HttpStatus.OK.value());
				responseInRepository.save(responseIn);
			}
		} catch (Exception ex) {
			throw new RuntimeException("Exception :"+Arrays.toString(ex.getStackTrace()));
		}
		return weatherData;
	}

	
	public List<Response_Out> getReportByCount(int page, int size, String field) {
		Pageable pageable = null;
		try {
			pageable = PageRequest.of(page, size, Direction.DESC, field);
		} catch (Exception ex) {
			throw new RuntimeException("Exception :"+Arrays.toString(ex.getStackTrace()));
		}
		return responseOutRepository.findAll(pageable).toList();
	}

}
