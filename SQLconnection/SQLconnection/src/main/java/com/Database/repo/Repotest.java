package com.Database.repo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


import com.Database.*;
import com.Database.ser.Employee;

@Repository

public class Repotest {
	@Autowired
	JdbcTemplate jdbc;
	
	static String query ="select * from VIGNESH_EMPLOYEES";
	public List<Employee> details(){
		List <Employee> emp = new ArrayList<Employee>();
		List <Map<String,Object>> rows = jdbc.queryForList(query);
		for (Map<String, Object> row : rows) {
			Employee empobj = new Employee();
			empobj.setID((int) row.get("ID"));
			empobj.setFIRST_NAME((String) row.get("FIRST_NAME"));
			empobj.setSALARY((int) row.get("SALARY"));
			emp.add(empobj);
		}
		return emp;		
	}
}
