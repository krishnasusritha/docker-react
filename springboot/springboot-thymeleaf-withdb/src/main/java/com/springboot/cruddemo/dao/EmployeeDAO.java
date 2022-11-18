package com.springboot.cruddemo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.cruddemo.entity.Employee;

public interface EmployeeDAO extends JpaRepository<Employee, Integer>{
	
	// method to sort by last name
	//Spring Data JPA creates a new method for us findAllBy + OrderBy + fieldName + order
	public List<Employee> findAllByOrderByLastAsc();

}
