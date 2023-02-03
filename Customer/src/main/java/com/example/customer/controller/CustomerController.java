package com.example.customer.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.customer.dao.CustomerRepository;
import com.example.customer.model.Customer;

import jakarta.transaction.Transactional;

@RestController
public class CustomerController {

	@Autowired
	private CustomerRepository repo;

	@Autowired
	private Environment env;

	Logger log = LogManager.getLogger("Customer");

	@GetMapping("/getall")
	public ResponseEntity<?> getAll() {
		List<Customer> c = new ArrayList<>();
		try {

			c = repo.findAll();
			System.out.println(c.toString());
			log.debug("list" + c);
			log.info("list" + c);

		} catch (Exception e) {

			log.error("error" + e);
			e.printStackTrace();

		}
		return new ResponseEntity<>(c, HttpStatus.OK);

	}

	@GetMapping("/uri")
	public ResponseEntity<?> read() {
		String tut = null;
		try {
			String tutorial = env.getProperty("url");
			RestTemplate template = new RestTemplate();
			tut = template.getForObject(tutorial, String.class);
			System.out.println(tut);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ResponseEntity<>(tut, HttpStatus.OK);
	}

	@GetMapping("/get")
	@Transactional
	public ResponseEntity<List> getAll1() {

		try {
			return new ResponseEntity<>(repo.getAllCustomer(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
