package com.ekram.spring.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

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
	private Map<Integer, Employee> empRepository = new HashMap<Integer, Employee>();
	
	@PostConstruct
	public void loadData(){
		Employee emp1 = new Employee();
		emp1.setCreatedDate(new Date());
		emp1.setId(nextEmpId());
		emp1.setName("Employee 1");
		
		empRepository.put(emp1.getId(), emp1);
		
		Employee emp2 = new Employee();
		emp2.setCreatedDate(new Date());
		emp2.setId(nextEmpId());
		emp2.setName("Employee 2");
		
		empRepository.put(emp2.getId(), emp2);
		
		Employee emp3 = new Employee();
		emp3.setCreatedDate(new Date());
		emp3.setId(nextEmpId());
		emp3.setName("Employee 3");
		
		empRepository.put(emp3.getId(), emp3);

		Employee emp4 = new Employee();
		emp4.setCreatedDate(new Date());
		emp4.setId(nextEmpId());
		emp4.setName("Employee 4");
		
		empRepository.put(emp4.getId(), emp4);
		
		Employee emp5 = new Employee();
		emp5.setCreatedDate(new Date());
		emp5.setId(nextEmpId());
		emp5.setName("Employee 5");
		
		empRepository.put(emp5.getId(), emp5);
		
		Employee emp6 = new Employee();
		emp6.setCreatedDate(new Date());
		emp6.setId(nextEmpId());
		emp6.setName("Employee 6");
		
		empRepository.put(emp6.getId(), emp6);
	}
	
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
