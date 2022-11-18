package com.springboot.cruddemo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


// Spring data rest dependency is added to pom.xml

// It gives us the employees endpoint directly
// employees - GET - fetch all employee objects
// employees/{EmployeeId} - GET 	- fetch employee object by id
// employees/{EmployeeId} - PUT 	- update employee object
//employees/{EmployeeId} - DELETE 	- delete employee object
//employees/ - POST 	- add/insert employee object
// employees?sort=first,last,asc
@Entity
@Table(name = "employee")
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	@Column(name = "first_name")
	private String first;
	@Column(name = "last_name")
	private String last;
	@Column(name = "email")
	private String email;
	
	public Employee() {
		super();
	}

	public Employee(String first, String last, String email) {
		super();
		this.first = first;
		this.last = last;
		this.email = email;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirst() {
		return first;
	}

	public void setFirst(String first) {
		this.first = first;
	}

	public String getLast() {
		return last;
	}

	public void setLast(String last) {
		this.last = last;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", first=" + first + ", last=" + last + ", email=" + email + "]";
	}

}
