package com.example.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CustomerApplication{

	public static void main(String[] args) {
		SpringApplication.run(CustomerApplication.class, args);
	}

	
//	@Autowired
//	CommentRepo c;
//	@Autowired
//	TutorialRepository t;
//	
//	@Override
//	public void run(String... args) throws Exception {
//		
//		Tutorial tut=new Tutorial(1,"pom","Principal of Management",true);
//		
//		t.save(tut);
//		
//		Comments comments = new Comments(1,"Excellent",tut);
//		
//		c.save(comments);
//		
//	}

}
