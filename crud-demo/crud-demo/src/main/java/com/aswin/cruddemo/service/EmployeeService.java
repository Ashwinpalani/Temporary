package com.aswin.cruddemo.service;

import java.util.List;

import com.aswin.cruddemo.entity.Employee;

public interface EmployeeService {

	public Employee createEmployee(Employee employee);

	public List<Employee> getAllEmployees();

	public Employee getById(Long id);

	public Employee updateEmployee(Employee employee);

	public List<Employee> findByFirstName(String firstName);

	public void deleteById(Long id);


}
