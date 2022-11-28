package com.microservices.employeecrudop.controller;

import com.microservices.employeecrudop.Repository.EmployeeRepository;
import com.microservices.employeecrudop.entity.CashManager;
import com.microservices.employeecrudop.entity.Employee;
import com.microservices.employeecrudop.sort.SortEmployeeBySalary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import java.util.stream.Collectors;

@RestController
public class EmployeeController {

	@Autowired
	EmployeeRepository employeeRepository;

//1//	
	@PostMapping(value = "/saveEmployee")
	public Employee createEmployee(@RequestBody Employee employee) {
		return employeeRepository.save(employee); // return from DB
	}

	// if no connection with database then we can use this to save and return
	// temporary
	// Id return will be 0
	HashMap<Integer, Employee> employeeList = new HashMap<>();

//2//	
	@PostMapping(value = "/createEmployee")
	public Employee saveMethod(@RequestBody Employee employee) {

		employeeList.put(((Employee) employee).getEmpId(), employee); // save in DB
		System.out.println(employeeList);
		return employeeList.get(employee.getEmpId()); // return from DB
	}

//3//	
	@GetMapping(value = "/getAllEmp")
	public String getMethod() {

//return employeeRepository.findAll();

// by using cashe calling
		return CashManager.cashe.values().parallelStream().collect(Collectors.toList()).toString();

	} 

//4 check working//
	@GetMapping(value = "/GetEmployee")
	public String getEmployee() {

		return "rahul";
	}

//5//	// get on time welcome message
	@PostMapping(value = "/getMessage/{name}")
	public HashMap<String, String> getMsgAfterLogin(@PathVariable String name) {

		HashMap<String, String> hashMap = new HashMap<String, String>();
		String getMessage = null;

		Date date = new Date();
		int hours = date.getHours();

		if (hours > 12 && hours < 17) {

			getMessage = "Good Morning " + name;

			hashMap.put("Time    ", String.valueOf(date));
			hashMap.put("Welcome ", getMessage);

		} else if (hours > 12 && hours < 17) {

			getMessage = "Good Afternoon " + name;

			hashMap.put("Time    ", String.valueOf(date));
			hashMap.put("Welcome ", getMessage);

		} else if (hours >= 17 && hours <= 19) {

			getMessage = "Good Evening " + name;

			hashMap.put("Time    ", String.valueOf(date));
			hashMap.put("Welcome ", getMessage);

		} else if (hours > 19 && hours <= 24) {

			getMessage = "Good Night " + name;

			hashMap.put("Time    ", String.valueOf(date));
			hashMap.put("Welcome ", getMessage);

		}
		return hashMap;

	}

//6//	
	@GetMapping(path = "/getEmployeeByID/{empId}")
	public String getEmployeeByID(@PathVariable int empId) {

		// Optional<Employee> employee = employeeRepository.findById(empId);
//i//
		Employee employee = CashManager.cashe.get(empId);

		{
			if (employee != null) {
				return employee.toString();
			} else {
				return "Employee not present in Database";
			}
			// you can return direct employee as well
		}
	}

//ii//	
	@PutMapping(path = "/updateEmployee/{empId}")
	public String updateEmployee(@RequestBody Employee employee, @PathVariable int empId) {

		Optional<Employee> employee1 = employeeRepository.findById(empId);
		if (employee1.isPresent()) {

			return employeeRepository.save(employee).toString();
		} else {
			return " Employee object is not present ";
		}
	}

	// 1st way
// updating existing employee by using taking user input and passing another employee reference

//	@PutMapping(path = "/updateEmployee1/{empId}")
//	public String updateEmployee1(@RequestBody Employee employee, @PathVariable int empId) {
//
//		Optional<Employee> employee1 = employeeRepository.findById(empId);
//		if (employee1.isPresent()) {
//
//			return employeeRepository.save(employee).toString();
//		} else {
//			return " Employee object is not present ";
//		}
//	}

// 2nd way transformation
	@PutMapping(path = "/updateEmployee1/{empId}")
	public String updateEmployee2(@RequestBody Employee employee, @PathVariable int empId) {
		// take employee from database and update respective employee by taking same
		// reference from user.
		Optional<Employee> updateEmployee1 = employeeRepository.findById(empId);
		if (updateEmployee1.isPresent()) {
			Employee employee2 = updateEmployee1.get();
			employee2.setName(employee.getName());
			employee2.setSalary(employee.getSalary());

			return employeeRepository.save(employee2).toString();
		} else {
			return "Employee not present of given Id";

		}
	}

//7//	

	@DeleteMapping(path = "/deleteEmployee/{empId}")
	public String deleteEmployee(@PathVariable int empId) {

		Optional<Employee> deleteEmployee = employeeRepository.findById(empId);
		if (deleteEmployee.isPresent()) {
			employeeRepository.deleteById(empId);
			return "Employee is Deleted";

		} else {
			return "Employee is not present of respective given ID ";
		}
	}

//8//	
	@GetMapping(path = "/getCount1")
	public String getCount1() {
		int count = 0;
		List<Employee> findAll = employeeRepository.findAll();
		for (Employee employee2 : findAll) {
			count++;
		}
		if (!findAll.isEmpty()) {
			return " Employee count :" + String.valueOf(count) + " nos";
		} else {
			return "Employee not present in Database";
		}
	}

//9//	
	@GetMapping(path = "/getCount")
	public String getCount() {
		long count = employeeRepository.count();
		if (count != 0) {
			return " Employee count :" + String.valueOf(count) + " nos";
		} else {
			return "Employee not present in Database";
		}

	}

//10//	
	@GetMapping(path = "/getMaxSalary")
	public String getMaxSalary() {
		List<Employee> findAll = CashManager.cashe.values().parallelStream().collect(Collectors.toList());
		Collections.sort(findAll, new SortEmployeeBySalary().reversed());
		Employee employee = findAll.get(0);
		if (employee != null) {
			return String.valueOf(employee);
		} else {
			return "Employee not present";
		}
	}

//11//	
	@GetMapping(path = "/getMinSalary")
	public String getMinSalary() {
		List<Employee> findAll = CashManager.cashe.values().parallelStream().collect(Collectors.toList());
		Collections.sort(findAll, new SortEmployeeBySalary());
		Employee employee = findAll.get(0);
		if (employee != null) {
			return String.valueOf(employee);
		} else {
			return "Employee not present";
		}
	}

	@GetMapping(path = "/getMaxSalaryBelow20000")
	public String getSalary() {
		List<Employee> findAll = CashManager.cashe.values().parallelStream()
				.filter(employee -> employee.getSalary() <= 20000).collect(Collectors.toList());
		Collections.sort(findAll, new SortEmployeeBySalary().reversed());
		Employee employee = findAll.get(0);
		if (employee != null) {
			return String.valueOf(employee);
		} else {
			return "Employee not present";
		}
	}

	
	@GetMapping(path = "/getSalaryBet20000to40000")
	public String getSalary2() {
		List<Employee> findAll = CashManager.cashe.values().parallelStream()
				.filter(employee -> employee.getSalary() >= 20000  && employee.getSalary() <=40000).collect(Collectors.toList());
			if (findAll != null) {
			return String.valueOf(findAll);
		} else {
			return "Employee between 20000 to 400000 not present";
		}
	}
}
