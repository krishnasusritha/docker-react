package com.springboot.cruddemo.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.cruddemo.dao.EmployeeDAO;
import com.springboot.cruddemo.entity.Employee;

@Service
public class EmployeeServiceImpl implements EmployeeService{

	private EmployeeDAO employeeDao;
	
	@Autowired
	public EmployeeServiceImpl(EmployeeDAO employeeDao) {
		super();
		this.employeeDao = employeeDao;
	}

	@Override
	@Transactional
	public List<Employee> findAll() {
		return employeeDao.findAll();
	}

	@Override
	@Transactional
	public Employee findById(int id) {
		return employeeDao.findById(id);
	}

	@Override
	@Transactional
	public void addEmployee(Employee employee) {
		employeeDao.addEmployee(employee);
	}

	@Override
	@Transactional
	public void deleteById(int id) {
		employeeDao.deleteById(id);
	}

}
