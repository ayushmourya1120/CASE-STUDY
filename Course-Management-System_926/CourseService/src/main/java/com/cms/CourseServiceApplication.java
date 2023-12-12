package com.cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
//@EnableAspectJAutoProxy
@ComponentScan(basePackages= {"com.cms.model","com.cms.controller","com.cms.service","com.cms.exception","com.cms.validation","com.*","com.cms.proxy"})
@EnableMongoRepositories("com.cms.repository")
public class CourseServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CourseServiceApplication.class, args);
	}

}
