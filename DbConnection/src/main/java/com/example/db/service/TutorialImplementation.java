package com.example.db.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.db.dao.TutorialRepository;
import com.example.db.model.Tutorial;
import com.example.db.utils.Utils;


@Service
public class TutorialImplementation implements TutorialRepository {
	
	@Autowired
	private JdbcTemplate jdbctemplate;
	
	@Autowired
	private Utils u;
	

	@Override
	public List<Tutorial> findAll() {
		//String sql ="Select * from tutorials";
		//System.out.println(u.getProperty("getall"));
		return jdbctemplate.query(u.getProperty("getall"), BeanPropertyRowMapper.newInstance(Tutorial.class));
	}

	@Override
	public int deleteById(int id) {
		//String sql= "Delete from tutorials where id =?";
		
		return jdbctemplate.update(u.getProperty("deletebyid"), id);
	}


	public Tutorial findById(int id) {
		//String sql ="Select * from tutorials where id=?";
		Tutorial tutorial = jdbctemplate.queryForObject(u.getProperty("getbyid"),BeanPropertyRowMapper.newInstance(Tutorial.class),id);
		return tutorial;
	}

	@Override
	public int updateById(int id) {
		//String sql ="update tutorials set id=1 where id=?";
		return jdbctemplate.update(u.getProperty("update"), id);
	}

	@Override
	public int insert(Tutorial tutorial) {
		//String sql ="insert into tutorials(id,title,description,published) values(?,?,?,?)";
		return jdbctemplate.update(u.getProperty("insert"),new Object[] {tutorial.getId(),tutorial.getTitle(),tutorial.getDescription(),tutorial.isPublished()});
		
	}

	@Override
	public int deleteAll() {
		//String sql ="delete tutorials";
		return jdbctemplate.update(u.getProperty("delete"));
	}
	
	
	
	

}
