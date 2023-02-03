package com.example.customer.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.customer.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	
//	@Procedure
//	List<Customer> getAllCustomer();
	
	 @Query(nativeQuery = true, value = "get_customer")
	    List<Customer> getAllCustomer();

}
