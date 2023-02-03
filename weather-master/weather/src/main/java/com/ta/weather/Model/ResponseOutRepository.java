package com.ta.weather.Model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ResponseOutRepository extends JpaRepository<Response_Out, String> {

	
}
