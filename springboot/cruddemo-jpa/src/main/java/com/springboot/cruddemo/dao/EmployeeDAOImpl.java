package com.springboot.cruddemo.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.springboot.cruddemo.entity.Employee;

@Repository
public class EmployeeDAOImpl implements EmployeeDAO{
	
	// field for entity manager
	private EntityManager entityManager;
	
	@Autowired
	public EmployeeDAOImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public List<Employee> findAll() {
		
		Query query = entityManager.createQuery("from Employee");
		
		List<Employee> results =  query.getResultList();
		
		return results;
		
	}

	@Override
	public Employee findById(int id) {
		
		return entityManager.find(Employee.class, id);		

	}

	@Override
	public void addEmployee(Employee employee) {
		Employee emp = entityManager.merge(employee);
		employee.setId(emp.getId());
		
	}

	@Override
	public void deleteById(int id) {

		Query query = entityManager.createQuery("delete from Employee where id=:empId");
		query.setParameter("empId", id);
		
		query.executeUpdate();
	}



}
