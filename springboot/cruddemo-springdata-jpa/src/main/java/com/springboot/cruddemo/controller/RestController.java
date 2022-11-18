package com.springboot.cruddemo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springboot.cruddemo.dao.EmployeeDAO;
import com.springboot.cruddemo.entity.Employee;
import com.springboot.cruddemo.service.EmployeeService;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class RestController {

	private EmployeeService employeeService;
	
	@Autowired
	public RestController(EmployeeService employeeService) {
		super();
		this.employeeService = employeeService;
	}

	@GetMapping("/")
	public String base() {
		return "Hello There";
	}
	
	@GetMapping("/employees")
	public List<Employee> getEmployees() {
		return employeeService.findAll();
	}
	
	@GetMapping("/employees/{id}")
	public Employee getEmployee(@PathVariable int id) {
		Employee emp = employeeService.findById(id);
		
		if(emp == null) {
			throw new RuntimeException("Employee not found in database " + id);
		}
		return emp;
	}

	@PostMapping("/employees/addEmployee")
	public Employee addEmployee(@RequestBody Employee employee) {
		System.out.println(employee.toString());
		employeeService.addEmployee(employee);
		
		return employee;
	}
	
	@PutMapping("/employees/updateEmployee")
	public Employee updateEmployee(@RequestBody Employee employee) {
		employeeService.addEmployee(employee);
		
		return employee;
	}
	
	@DeleteMapping("/employees/{id}")
	public String deleteEmployee(@PathVariable int id) {
		Employee emp = employeeService.findById(id);
		
		if(emp == null) {
			throw new RuntimeException("Employee not found in database " + id);
		}
		employeeService.deleteById(id);

		return "Deleted emp id with " + id;
	}
	
	
}
