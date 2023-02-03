package com.example.ak;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.ak.model.Employee;
import com.example.ak.model.Example;
import com.example.ak.model.TotalDetails;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class JsontoJavaApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(JsontoJavaApplication.class, args);
	}

	public void run(String... args) throws Exception {
		
		Example t = null;
		try {
			byte[] jsonData = Files.readAllBytes(Paths.get("D:\\Example.txt"));
			ObjectMapper o = new ObjectMapper();
			t = o.readValue(jsonData, Example.class);
			System.out.println("\nEmployee Object 1. \n" + t);
			//return new ResponseEntity<>(t, HttpStatus.OK);

		} catch (IOException e) {

			e.printStackTrace();
		}

		//return new ResponseEntity<>(t, HttpStatus.OK);
		
		
		TotalDetails t1 = null;
		try {
			byte[] jsonData = Files.readAllBytes(Paths.get("D:\\Jsondata.txt"));
			ObjectMapper o = new ObjectMapper();
			t1 = o.readValue(jsonData, TotalDetails.class);
			System.out.println("\nEmployee Object 2.\n" + t1);
			//return t;

		} catch (IOException e) {

			e.printStackTrace();
		}
		//return t;
	
	Employee t2 = null;
	try {
		byte[] jsonData = Files.readAllBytes(Paths.get("D:\\Emp.txt"));
		ObjectMapper o = new ObjectMapper();
		t2 = o.readValue(jsonData, Employee.class);
		System.out.println("\n*****Employee Object****** 3.\n" + t);
		//return t;

	} catch (IOException e) {

		e.printStackTrace();
	}

	//return t;

}
}

