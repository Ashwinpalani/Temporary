package com.example.ak.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.ak.model.Employee;
import com.example.ak.model.Example;
import com.example.ak.model.TotalDetails;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController // should not use controller to avoid circular view path exception
public class JsonController {
	
	@Autowired
	private Environment env;
	

	@GetMapping("/get")
	public TotalDetails convert() {
		TotalDetails t = null;
		try {
			byte[] jsonData = Files.readAllBytes(Paths.get("D:\\Jsondata.txt"));
			ObjectMapper o = new ObjectMapper();
			t = o.readValue(jsonData, TotalDetails.class);
			System.out.println("\nEmployee Object\n" + t);
			return t;

		} catch (IOException e) {

			e.printStackTrace();
		}
		return t;

	}

	@GetMapping("/getall")
	public ResponseEntity<?> Task() {
		Example t = null;
		try {
			byte[] jsonData = Files.readAllBytes(Paths.get("D:\\Example.txt"));
			ObjectMapper o = new ObjectMapper();
			t = o.readValue(jsonData, Example.class);
			System.out.println("\n*****Employee Object******\n" + t);
			return new ResponseEntity<>(t, HttpStatus.OK);

		} catch (IOException e) {

			e.printStackTrace();
		}

		return new ResponseEntity<>(t, HttpStatus.OK);
	}
	
	@GetMapping("/gets")
	public Employee Ntask() {
		Employee t = null;
		try {
			byte[] jsonData = Files.readAllBytes(Paths.get("D:\\Emp.txt"));
			ObjectMapper o = new ObjectMapper();
			t = o.readValue(jsonData, Employee.class);
			System.out.println("\n*****Employee Object******\n" + t);
			return t;

		} catch (IOException e) {

			e.printStackTrace();
		}

		return t;
	}
	
	@GetMapping("/api")
	public Example consume()
	{
		Example t = null;
		try {
		//String url = "http://172.16.11.30:8080/demo2/json2";
		String url = env.getProperty("url");
		RestTemplate r =new RestTemplate();
		t = r.getForObject(url, Example.class);
		return t;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}
	
	
}
