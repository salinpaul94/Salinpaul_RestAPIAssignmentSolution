package com.greatlearning.employee.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.greatlearning.employee.dao.EmployeeRepository;
import com.greatlearning.employee.entity.Employee;

@RestController
public class SearchController {
	
	@Autowired
	private EmployeeRepository employeeRepository;

	//search handler
	@GetMapping("/search/{query}")
	public ResponseEntity<?> search(@PathVariable("query") String query)
	{
		System.out.println(query);
		List<Employee> employee = this.employeeRepository.findByFirstNameContaining(query);
		
		return ResponseEntity.ok(employee);
	}
}
