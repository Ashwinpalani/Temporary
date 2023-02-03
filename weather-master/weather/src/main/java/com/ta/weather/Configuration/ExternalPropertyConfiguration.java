package com.ta.weather.Configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import lombok.extern.log4j.Log4j2;
@Log4j2
@Configuration
@PropertySource(value= {"file:E:/WeatherScheduler/configFile/application.properties"})
public class ExternalPropertyConfiguration {
	private static Properties propertyFile;
	private static long lastModified;
	private static boolean reload;
	
	  @Autowired
	    Environment env;
	  
	public void getPropertyDetails() {
		String PropertyFile ="";
		try {
		  File file=new File(env.getProperty("value"));
		   lastModified = file.lastModified();
		//  properties.load(new FileInputStream(filename));
		}catch(Exception e) {
			
		}
	}
	public  boolean loadProperties(String propertiesPath ) {
		try(InputStream inputStream=new FileInputStream(propertiesPath)) {
			log.info("Property file location: "+propertiesPath);
			if(propertyFile==null) {
				File file=new File(propertiesPath);
				lastModified=file.lastModified();
				propertyFile = new Properties();
				propertyFile.load(inputStream);
				log.info("PROPERTY LOADED");
				setReload(true);
			}
			else {
				File file=new File(propertiesPath);
				if(lastModified!=file.lastModified()) {
					propertyFile = new Properties();
					propertyFile.load(inputStream);
					log.info("PROPERTY FILE - MODIFIED");
					setReload(true);
				}
				else {
					log.info("ALREADY_LOADED_PROPERTY ");
					setReload(false);
				}
			}
//			mySession.getVariableField("propertyFile").setValue(propertyFile);
//			TraceInfo.trace(ITraceInfo.TRACE_LEVEL_INFO,"_______________PROPERTY_DETAILS________________",mySession);
//			TraceInfo.trace(ITraceInfo.TRACE_LEVEL_INFO, " PROPERTY  : "+propertyFile, mySession);
//			TraceInfo.trace(ITraceInfo.TRACE_LEVEL_INFO,"_______________CLOSED________________",mySession);
			return true;
		} catch (IOException e) {
			log.info(Arrays.toString(e.getStackTrace()));
		}
		return false;
	}
	
	public  Properties getProperties() {
		return propertyFile;
	}

	public  boolean isReload() {
		return reload;
	}

	private void setReload(boolean reload) {
		ExternalPropertyConfiguration.reload = reload;
	}
}
