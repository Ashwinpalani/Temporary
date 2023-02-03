package com.ta.weather.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
	@Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
        		.select()
        		.paths(PathSelectors.any())
        		.apis(RequestHandlerSelectors.basePackage("com.ta.weather"))
        		.build()
        		.apiInfo(apiEndPointInfo());
    }
  
	 public ApiInfo apiEndPointInfo(){
	        return new ApiInfoBuilder().title("Spring Boot Rest API")
	                .description("Weather API")
	                .license("Apache 2.0")
	                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
	                .version("0.0.1-SNAPSHOT")
	                .build();
	    }
	 @Bean
	 public UiConfiguration uiConfig() {
		return UiConfigurationBuilder.builder()
				.filter(false)
				.build();
		
	 }
}
  
