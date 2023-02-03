package com.example.db.dao;

import java.util.List;

import com.example.db.model.Tutorial;

public interface TutorialRepository {
	
	List<Tutorial> findAll();
	
	Tutorial findById(int id);
	
	int deleteById(int id);
	
	int updateById(int id);
	
	int insert(Tutorial tutorial);
	
	int deleteAll();
	

}
