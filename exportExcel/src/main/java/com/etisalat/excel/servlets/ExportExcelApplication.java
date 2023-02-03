package com.etisalat.excel.servlets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"com.etisalat.excel"})
@EnableScheduling
public class ExportExcelApplication{

	public static void main(String[] args) {
		SpringApplication.run(ExportExcelApplication.class, args);
	}

}
