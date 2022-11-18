package com.springboot.cruddemo.entity;

public class Employee {
	private int id;
	private String first;
	private String last;
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
