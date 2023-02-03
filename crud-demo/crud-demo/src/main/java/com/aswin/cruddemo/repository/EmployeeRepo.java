package com.aswin.cruddemo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aswin.cruddemo.entity.Employee;

public interface EmployeeRepo extends JpaRepository<Employee, Long>{
		
	List<Employee> findByFirstName(String firstName);
}
