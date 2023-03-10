package com.ta.weather.Schedule;


import java.io.IOException;
import java.util.Properties;

import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

import lombok.extern.log4j.Log4j2;
@Log4j2
@Configuration
public class SchedulerConfiguration {
	@Bean
	 public JobFactory jobFactory(ApplicationContext applicationContext) {
	  SpringJobFactory jobFactory = new SpringJobFactory();
	  jobFactory.setApplicationContext(applicationContext);
	  return jobFactory;
	 }
	 @Bean
	 public SchedulerFactoryBean schedulerFactoryBean(JobFactory jobFactory,
	  Trigger simpleJobTrigger) throws IOException {
	  SchedulerFactoryBean factory = new SchedulerFactoryBean();
	  factory.setJobFactory(jobFactory);
	  factory.setQuartzProperties(quartzProperties());
	  factory.setTriggers(simpleJobTrigger);
	  log.info("starting jobs....");
	  return factory;
	 }
	 @Bean
	 public SimpleTriggerFactoryBean simpleJobTrigger(
	  @Qualifier("simpleJobDetail") JobDetail jobDetail,
	  @Value("${simplejob.frequency}") long frequency) {
	  System.out.println("simpleJobTrigger");
	  SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
	  factoryBean.setJobDetail(jobDetail);
	  factoryBean.setStartDelay(0);
	  factoryBean.setRepeatInterval(frequency);
	  factoryBean.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
	  return factoryBean;
	 }
	 @Bean
	 public Properties quartzProperties() throws IOException {
	  PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
	  propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
	  propertiesFactoryBean.afterPropertiesSet();
	  return propertiesFactoryBean.getObject();
	 }
	 @Bean
	 public JobDetailFactoryBean simpleJobDetail() {
	  JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
	  factoryBean.setJobClass(QuartzJob.class);
	  factoryBean.setDurability(true);
	  return factoryBean;
	 }
	}