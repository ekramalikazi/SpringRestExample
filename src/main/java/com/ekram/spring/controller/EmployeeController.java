package com.ekram.spring.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ekram.spring.constants.EmpRestURIConstants;
import com.ekram.spring.controller.exception.ResourceNotFoundException;
import com.ekram.spring.model.Employee;
import com.ekram.spring.service.EmployeeService;

@RestController
public class EmployeeController {

	private static final Logger LOG = LogManager.getLogger();

	@Autowired EmployeeService employeeService;
	
	@RequestMapping(value = EmpRestURIConstants.OPTION_EMP, method = RequestMethod.OPTIONS)
    public ResponseEntity<Void> discover()
    {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Allow", "OPTIONS,HEAD,GET,POST");
        return new ResponseEntity<>(null, headers, HttpStatus.NO_CONTENT);
    }

	@RequestMapping(value = EmpRestURIConstants.DUMMY_EMP, 
			produces={MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE},
			method = RequestMethod.GET)
	public ResponseEntity<Employee> getDummyEmployee() {
		LOG.info("Start getDummyEmployee");
		Employee emp = new Employee();
		emp.setId(9999);
		emp.setName("Dummy");
		emp.setCreatedDate(new Date());
		
		Employee empCreated = employeeService.create(emp);

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("HeaderKey", "HeaderData");
		return new ResponseEntity<Employee>(empCreated, responseHeaders, HttpStatus.FOUND);
	}

	@RequestMapping(value = EmpRestURIConstants.GET_EMP, 
			produces={MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE},
			method = RequestMethod.GET)
	public ResponseEntity<Employee> getEmployee(@PathVariable("id") int empId) {
		LOG.info("Start getEmployee. ID="+empId);
		Employee emp = employeeService.getEmployee(empId);

		if (emp == null){
			throw new ResourceNotFoundException();
		}

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("HeaderKey", "HeaderData");
		return new ResponseEntity<Employee>(emp, responseHeaders, HttpStatus.FOUND);
	}

	//TODO: need to revisit
	@RequestMapping(value = EmpRestURIConstants.GET_ALL_EMP, 
			produces={MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE},
			method = RequestMethod.GET)
	public ResponseEntity<List<Employee>> getAllEmployees() {
		LOG.info("Start getAllEmployees.");
		
		List<Employee> emps = employeeService.getEmployees();

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("HeaderKey", "HeaderData");
		return new ResponseEntity<List<Employee>>(emps, responseHeaders, HttpStatus.FOUND);
	}

	@RequestMapping(value = EmpRestURIConstants.CREATE_EMP,
			produces={MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE},
			method = RequestMethod.POST)
	public ResponseEntity<Employee> createEmployee(@RequestBody @Valid Employee emp) {
		LOG.info("Start createEmployee.");

		Employee empCreated = employeeService.create(emp);
		
		
		String uri = ServletUriComponentsBuilder.fromCurrentServletMapping()
                .path(EmpRestURIConstants.GET_EMP).buildAndExpand(empCreated.getId()).toString();
        
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("HeaderKey", "HeaderData");
		responseHeaders.add("Location", uri);
		return new ResponseEntity<Employee>(empCreated, responseHeaders, HttpStatus.CREATED);
	}

	@RequestMapping(value = EmpRestURIConstants.DELETE_EMP, 
			produces={MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE},
			method = RequestMethod.PUT)
	public @ResponseBody Employee deleteEmployee(@PathVariable("id") int empId) {
		LOG.info("Start deleteEmployee.");
		
		return employeeService.delete(empId);
		
	}

}
