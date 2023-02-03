package com.aswin.cruddemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aswin.cruddemo.entity.Employee;
import com.aswin.cruddemo.service.EmployeeService;

@RestController
@RequestMapping("/emp")
public class EmployeeController {
		
	
	@Autowired
	private EmployeeService employeeService;
	
	
	@PostMapping("/save")
	public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee){
		Employee saveEmployee = employeeService.createEmployee(employee);
		return new ResponseEntity<Employee>(saveEmployee,HttpStatus.CREATED);
	}
	
	@GetMapping("/all")
	public  ResponseEntity<List<Employee>> getAllEmployees(){
		List<Employee> employee = employeeService.getAllEmployees();
		return new ResponseEntity<List<Employee>>(employee,HttpStatus.OK);
	}
	@GetMapping("/")
	public ResponseEntity<Employee> getById(@RequestParam("id") Long id){
		Employee employee1 = employeeService.getById(id);
		return new ResponseEntity<Employee>(employee1,HttpStatus.OK);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee,@PathVariable("id") long id){
		Employee emp0 = employeeService.getById(id);
		emp0 = employeeService.updateEmployee(employee);
		//Employee employee1 = employeeService.updateEmployee(employee);
		
		return new ResponseEntity<Employee>(emp0,HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/name/{name}")
	public ResponseEntity<List<Employee>> findByFirstName(@PathVariable("name") String firstName){
		List<Employee> emp1 = employeeService.findByFirstName(firstName);
		
		return new ResponseEntity<List<Employee>>(emp1,HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteDetails(@PathVariable long id)
	{
	    employeeService.deleteById(id);
		return new ResponseEntity<>("deleted sucessfully", HttpStatus.OK);
	}
	
	
	
}
