package com.microservices.employeecrudop.entity;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.microservices.employeecrudop.Repository.EmployeeRepository;

@Component          //  create bean of class
public class CashManager {

	@Autowired
	EmployeeRepository employeeRepository;

	// This is my cashe
	public static HashMap<Integer, Employee> cashe = new HashMap<>();

	
	
	@Scheduled(cron = "* * * * * *")  // every second calling
	public void loadCashe() {

		// find employees from DB and put one by one employee in cashe
		// key employeeID , value = employee

		
		
		
		//-----> 
		System.out.println("cashe loading");
		List<Employee> employeeList = employeeRepository.findAll();
// one way		
		employeeList.parallelStream().forEach(employee -> cashe.put(employee.getEmpId(), employee));
		System.out.println("cashe ending");
		
// another way		
//	for (Employee employee2 : employeeList) {
//		cashe.put(employee2.getEmpId(), employee2);
//	} 

	}
}
