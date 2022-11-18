package com.springboot.cruddemo.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springboot.cruddemo.entity.Employee;

@Controller
@RequestMapping("/api")
public class RestController {
	
	@GetMapping("/hello")
	public String welcome (Model model) {
		model.addAttribute("date" , new Date());
		return "helloWorld";
	}
	
	@GetMapping("/employees")
	public String getEmployee (Model model) {
		Employee emp1 = new Employee("first1", "last1", "first1@gmail.com");
		Employee emp2 = new Employee("first2", "last2", "first2@gmail.com");
		Employee emp3 = new Employee("first3", "last3", "first3@gmail.com");
		Employee emp4 = new Employee("first4", "last4", "first4@gmail.com");
		
		List<Employee> list = new ArrayList<Employee>();
		
		list.add(emp1);
		list.add(emp2);
		list.add(emp3);
		list.add(emp4);
		
		model.addAttribute("employees" , list);
		return "list-employees";
	}
	
}
