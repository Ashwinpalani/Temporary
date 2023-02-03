package com.aswin.cruddemo.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableWebSecurity
public class Securitymvc {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http)
	{
//		 http.csrf().disable()
//         .authorizeRequests()
//         .antMatchers("/register/**").permitAll()
//         .antMatchers("/index").permitAll()
//		   .and()
		return null;
		
	}
	
	
}
