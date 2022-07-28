package com.greatlearning.employee.service;


import java.util.List;


import com.greatlearning.employee.entity.Employee;


public interface EmployeeServiceInterface {
	public List<Employee> findAll();
	public Employee findById(int Id);
	public void save(Employee employee);
	public void deleteById(int id);
	

}