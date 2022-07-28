package com.greatlearning.employee.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greatlearning.employee.dao.EmployeeRepository;
import com.greatlearning.employee.entity.Employee;

@Service
public class EmployeeServiceImplementation implements EmployeeServiceInterface {

	@Autowired
	EmployeeRepository employeeRepository;
	

	@Override
	public List<Employee> findAll() {
		List<Employee> employees = employeeRepository.findAll();
		return employees;
	}

	@Override
	public Employee findById(int id) {
		Optional<Employee> employee;
		employee = employeeRepository.findById(id);
		return employee.get();
	}

	@Override
	public void save(Employee employee) {
		employeeRepository.save(employee);
		
	}

	@Override
	public void deleteById(int id) {
		employeeRepository.deleteById(id);
		
	}
	
	

}
