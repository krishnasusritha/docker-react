package com.springboot.cruddemo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.cruddemo.entity.Employee;

@Repository
public interface EmployeeDAO extends JpaRepository<Employee, Integer>{
	

}
