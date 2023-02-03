package com.ta.weather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableSwagger2
//@EnableScheduling
public class WeatherApplication {
	public static void main(String[] args) {
		SpringApplication.run(WeatherApplication.class, args);
	}
	}

