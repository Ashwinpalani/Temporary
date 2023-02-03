package com.ta.weather.Configuration;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.ta.weather.Model.PropertyConstants;



@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "primaryEntityManagerFactory", transactionManagerRef = "primaryTransactionManager", 
basePackages = {
		"com.ta.weather.Model" })
public class DatabaseConfiguration {
	@Autowired
	PropertyConstants propertyConstants;
	
	@Primary
		@Bean(name = "primaryDataSource")
	public DataSource primaryDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUrl(propertyConstants.getUrl());
		dataSource.setUsername(propertyConstants.getUsername());
		String password = AESPasswordConfiguration.decrypt(propertyConstants.getPassword());
		dataSource.setPassword(password);
		dataSource.setDriverClassName(propertyConstants.getDriverClassName());
		return dataSource;
	}
	
	@Primary
	@Bean(name = "primaryEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean primaryEntityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier("primaryDataSource") DataSource primaryDataSource) {
		return builder.dataSource(primaryDataSource).packages("com.ta.weather.Model").build();
	}
	
	@Primary
	@Bean(name = "primaryTransactionManager")
	public PlatformTransactionManager primaryTransactionManager(
			@Qualifier("primaryEntityManagerFactory") EntityManagerFactory primaryEntityManagerFactory) {
		return new JpaTransactionManager(primaryEntityManagerFactory);
	}
	
	

	}
