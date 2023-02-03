package com.example.customer.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.customer.dao.TutorialRepository;
import com.example.customer.model.Tutorial;

@RestController
@RequestMapping("/api")
public class TutorialController {

	@Autowired
	private TutorialRepository repo;

	Logger log1 = LogManager.getLogger("Tutorial");

	@PostMapping("/save")
	public ResponseEntity<Tutorial> create(@RequestBody Tutorial tutorial) {
		Tutorial tut = repo.save(tutorial);
		return new ResponseEntity<Tutorial>(tut, HttpStatus.OK);
	}

	@GetMapping("/get")
	public ResponseEntity<?> getAll() {
		List<Tutorial> list = repo.findAll();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@GetMapping("/getbyid")
	public ResponseEntity<?> getbyid(long id) {
		Tutorial person = repo.findById(id).get();
		return new ResponseEntity<>(person, HttpStatus.OK);

	}

	@PutMapping("/update")
	public ResponseEntity<?> update(@RequestBody Tutorial tutorial, long id) {
		Tutorial tut1 = repo.findById(id).get();
		if (tut1 != null) {
			tut1.setId(tut1.getId());
			tut1.setTitle(tut1.getTitle());
			tut1.setDescription(tut1.getDescription());
			tut1.setPublished(tut1.isPublished());
			
		}
		return new ResponseEntity<>(tut1, HttpStatus.OK);
	}

	@DeleteMapping("/del")
	public ResponseEntity<?> delete() {
		repo.deleteAll();
		return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
	}

	@DeleteMapping("/delbyid")
	public ResponseEntity<?> deletebyid(long id) {
		repo.deleteById(id);
		return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
	}
}
