package com.ta.weather.Schedule;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.ta.weather.Dao.ResponseDao;

import lombok.extern.log4j.Log4j2;
@Log4j2
@RestController
public class QuartzJob implements Job {
	@Autowired 
	ResponseDao responseDao;
		 @Override
		 public void execute(JobExecutionContext jobExecutionContext) {
			 String time = null;
				log.info("Scheduler Started");
				LocalDateTime localDateTime = java.time.LocalDateTime.now();
				ResponseEntity<Object> weatherData = null;
				try {
				time = localDateTime.toString();
				time = time.replace("T"," ");	
				weatherData = responseDao.getWeatherData();
				if(weatherData != null) {
					log.info(time+":"+weatherData);
				}
				else {
					log.info("DATA NOT-FOUND");
				}
				}catch (Exception e) {
					log.error("Exception : " + Arrays.toString(e.getStackTrace()));
				}
		 }
}

