package com.springboot.cruddemo.dao;

import java.util.List;

import com.springboot.cruddemo.entity.Employee;

public interface EmployeeDAO {
	
	public List<Employee> findAll();
	
	public Employee findById(int id);
	
	public void addEmployee(Employee employee);
	
	public void deleteById(int id);

}
