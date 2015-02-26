package com.ekram.spring.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ekram.spring.model.Employee;

@Service("employeeService")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class EmployeeServiceImpl implements EmployeeService {

	private static final Logger LOG = LogManager.getLogger();
	
	private volatile int empIdSequence = 1;
	
	//Map to store employees, ideally we should use database
	Map<Integer, Employee> empRepository = new HashMap<Integer, Employee>();
	
	@Override
	public Employee getEmployee(int empId) {
		
		LOG.debug("getEmployee - Employee ID {}", empId);
		return empRepository.get(empId);
	}

	@Override
	public List<Employee> getEmployees() {
		List<Employee> emps = new ArrayList<Employee>();
		Set<Integer> empIdKeys = empRepository.keySet();
		for(Integer i : empIdKeys){
			emps.add(empRepository.get(i));
		}
		return emps;
	}

	@Override
	public Employee create(Employee emp) {
		if(emp.getId() < 1){
			emp.setId(nextEmpId());
		}

		emp.setCreatedDate(new Date());
		empRepository.put(emp.getId(), emp);
		
		return emp;
	}

	@Override
	public Employee delete(int empId) {
		Employee emp = empRepository.get(empId);
		empRepository.remove(empId);
		return emp;
	}
	
	private synchronized int nextEmpId(){
		return empIdSequence++;
	}
}
