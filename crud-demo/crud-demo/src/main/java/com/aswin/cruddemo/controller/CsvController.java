package com.aswin.cruddemo.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.aswin.cruddemo.entity.CsvEmployee;
import com.aswin.cruddemo.entity.Employee;


@RestController
@RequestMapping("/api")
public class CsvController {
	
	@Autowired
	private CsvEmployee csv;
	
	@GetMapping("/download")
	public void exportToCsv(HttpServletResponse response) throws IOException
	{
		response.setContentType("text/csv");
		SimpleDateFormat format = new SimpleDateFormat("dd-mm-yyyy hh:mm:ss");
		String currentDateTime = format.format(new Date());
		 String headerkey = "Content-Disposition";
		 String headervalue = "attachment; filename=EmployeeDetails_" + currentDateTime + ".csv";
		 
		 response.setHeader(headerkey, headervalue);
		 
		 List<Employee> list = csv.listAll();
		 ICsvBeanWriter writer = new CsvBeanWriter(response.getWriter(),CsvPreference.STANDARD_PREFERENCE);
		 String  csvheader[] = {"id","firstname","lastname","email"};
		 String nameMapping[] = {"id","firstName","lastName","email"};
		 
		  writer.writeHeader(csvheader);
	         
	        for (Employee csv : list) {
				writer.write(csv, nameMapping);
	        }
	         
	        writer.close();
	         
	    }
	
	@GetMapping("/export")
	public String page()
	{
		
		return"csv";
	}
}
