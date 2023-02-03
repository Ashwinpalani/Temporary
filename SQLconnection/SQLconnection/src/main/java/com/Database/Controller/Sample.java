package com.Database.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Database.repo.Repotest;
import com.Database.ser.Employee;

@RestController
@RequestMapping("/hello/")
public class Sample {

	@Autowired
	Repotest obj;

	@RequestMapping(value = "test", method = RequestMethod.GET)

	public ResponseEntity<?> customerInformation() {

		List<Employee> emp = obj.details();

		return new ResponseEntity<>(emp, HttpStatus.OK);
	}
}
