package com.springboot.cruddemo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.cruddemo.dao.EmployeeDAO;
import com.springboot.cruddemo.entity.Employee;


// @Transactional is not required since JPARepository provides us by default
@Service
public class EmployeeServiceImpl implements EmployeeService{

	private EmployeeDAO employeeDao;
	
	@Autowired
	public EmployeeServiceImpl(EmployeeDAO employeeDao) {
		super();
		this.employeeDao = employeeDao;
	}

	@Override
	public List<Employee> findAll() {
		return employeeDao.findAll();
	}

	@Override
	public Employee findById(int id) {
		Optional<Employee> result = employeeDao.findById(id);
		Employee emp = null;
		if(result.isPresent()) {
			emp = result.get();
		} else {
			throw new RuntimeException(" Cannot find Employee by Id : " + id);
		}
		return emp;
	}

	@Override
	public void addEmployee(Employee employee) {
		employeeDao.save(employee);
	}

	@Override
	public void deleteById(int id) {
		employeeDao.deleteById(id);
	}

}
