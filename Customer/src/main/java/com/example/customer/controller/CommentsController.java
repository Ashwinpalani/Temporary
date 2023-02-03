package com.example.customer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.customer.dao.CommentRepo;
import com.example.customer.model.Comments;

@RestController
@RequestMapping("/co")
public class CommentsController {
	
	@Autowired
	private CommentRepo c;
	
	@PostMapping("/postc")
	public ResponseEntity<Comments> insert(@RequestBody Comments comments) 
	{
		Comments comm = c.save(comments);
		return new ResponseEntity<Comments>(comm, HttpStatus.OK);
	}
	
	@GetMapping("/getc")
	public  ResponseEntity<?> retrive()
	{
		List<Comments> list = c.findAll(); 
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	

}
