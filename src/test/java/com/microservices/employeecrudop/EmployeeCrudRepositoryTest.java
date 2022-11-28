package com.microservices.employeecrudop;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


@SpringBootTest
public class EmployeeCrudRepositoryTest {

	@Test
	public void contextLoads() {

	}
	@Test
	public void findAllEmployeeTest() throws URISyntaxException {
		System.out.println("Test Started");
		RestTemplate restTemplate = new RestTemplate();
		// uniform resource locator
		String url = "http://localhost:8080/getAllEmp"; // This can be created dynamically
		// uniform resource identifier
		URI uri = new URI(url);
		ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);

		Assertions.assertEquals(200, response.getStatusCodeValue());
		System.out.println("Test Ended");
	} 
}


