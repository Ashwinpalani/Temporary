package com.aswin.cruddemo.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aswin.cruddemo.entity.Employee;
import com.aswin.cruddemo.repository.EmployeeRepo;
import com.aswin.cruddemo.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepo employeeRepo;

	@Override
	public Employee createEmployee(Employee employee) {
		Employee employee1 = employeeRepo.save(employee);
		return employee1;
	}

	@Override
	public List<Employee> getAllEmployees() {

		List<Employee> employee = employeeRepo.findAll();

		return employee;
	}

	@Override
	public Employee getById(Long id) {
		Employee employee2 = employeeRepo.findById(id).get();
		return employee2;
	}

	@Override
	public Employee updateEmployee(Employee employee) {
		Employee emp = new Employee();
		emp.setId(employee.getId());
		emp.setFirstName(employee.getFirstName());
		emp.setLastName(employee.getLastName());
		emp.setEmail(employee.getEmail());
		emp.setAddress(employee.getAddress());
		Employee emp1 = employeeRepo.save(emp);
		return emp1;
	}

	@Override
	public List<Employee> findByFirstName(String firstName) {

		List<Employee> employee = employeeRepo.findByFirstName(firstName);

		return employee;
	}

	@Override
	public void deleteById(Long id) {
		employeeRepo.deleteById(id);

	}

}
