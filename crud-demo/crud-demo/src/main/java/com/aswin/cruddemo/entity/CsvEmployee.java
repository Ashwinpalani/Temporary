package com.aswin.cruddemo.entity;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.aswin.cruddemo.repository.EmployeeRepo;

@Service
@Transactional
public class CsvEmployee {
	
	
	@Autowired
    private EmployeeRepo  repo;
     
    public List<Employee> listAll() {
        return repo.findAll(Sort.by("id").ascending());
	

}
}
