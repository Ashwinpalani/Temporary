package com.example.db.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class Utils {
	
	@Autowired
	private Environment env;
	
	
	public String getProperty(String query)
	{
		return env.getProperty(query);
	}

}
