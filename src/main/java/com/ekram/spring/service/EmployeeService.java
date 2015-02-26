package com.ekram.spring.service;

import java.util.List;

import com.ekram.spring.model.Employee;

public interface EmployeeService {

	Employee getEmployee(int empId);

	List<Employee> getEmployees();

	Employee create(Employee emp);

	Employee delete(int empId);
	
	

}
