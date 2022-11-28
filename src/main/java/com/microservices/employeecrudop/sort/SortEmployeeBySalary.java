package com.microservices.employeecrudop.sort;

import java.util.Comparator;

import com.microservices.employeecrudop.entity.Employee;

public class SortEmployeeBySalary implements Comparator<Employee> {

	@Override
	public int compare(Employee o1, Employee o2) {

		return o1.getSalary().compareTo(o2.getSalary());
	}

}
