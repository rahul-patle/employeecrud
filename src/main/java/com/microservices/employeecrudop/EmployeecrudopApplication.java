package com.microservices.employeecrudop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling      // for scheduling
@SpringBootApplication
public class EmployeecrudopApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeecrudopApplication.class, args);
	}

}
