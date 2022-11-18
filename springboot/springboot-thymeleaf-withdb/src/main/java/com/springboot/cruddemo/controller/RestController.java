package com.springboot.cruddemo.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.springboot.cruddemo.entity.Employee;
import com.springboot.cruddemo.service.EmployeeService;

@Controller
@RequestMapping("/employees")
public class RestController {
//	private List<Employee> list;

	EmployeeService employeeService;

	@Autowired
	public RestController(EmployeeService employeeService) {
		super();
		this.employeeService = employeeService;
	}

	@GetMapping("/hello")
	public String welcome(Model model) {
		model.addAttribute("date", new Date());
		return "helloWorld";
	}
	
	
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model model) {

		Employee emp = new Employee();
		model.addAttribute("employee", emp);
		return "/employees/employee-form";
	}
	
	
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("employeeId") int id, Model model) {

		Employee emp = employeeService.findById(id);
		model.addAttribute("employee", emp);
		return "/employees/employee-form";
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam("employeeId") int id) {

		 employeeService.deleteById(id);
		return "redirect:/employees/list";
	}

	@GetMapping("/list")
	public String getEmployee(Model model) {

		model.addAttribute("employees", employeeService.findAll());
		return "employees/list-employees";
	}
	
	@PostMapping("/save")
	public String addEmployee(@ModelAttribute Employee employee) {
		
		employeeService.addEmployee(employee);
		
		// redirect to prevent duplicate submissions
		return "redirect:/employees/list";
	}

//	@PostConstruct
//	private List<Employee> postConstruct() {
//		Employee emp1 = new Employee("first1", "last1", "first1@gmail.com");
//		Employee emp2 = new Employee("first2", "last2", "first2@gmail.com");
//		Employee emp3 = new Employee("first3", "last3", "first3@gmail.com");
//		Employee emp4 = new Employee("first4", "last4", "first4@gmail.com");
//
//		list = new ArrayList<Employee>();
//
//		list.add(emp1);
//		list.add(emp2);
//		list.add(emp3);
//		list.add(emp4);
//		return list;
//	}

}
