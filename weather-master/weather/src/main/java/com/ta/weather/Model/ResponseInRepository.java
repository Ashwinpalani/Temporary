package com.ta.weather.Model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponseInRepository extends JpaRepository<Response_In, String> {


	
}
