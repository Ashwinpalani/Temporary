package com.example.customer.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.customer.model.Tutorial;

public interface TutorialRepository extends JpaRepository<Tutorial, Long>{
		

}
