package com;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class sample1 {
	
	private String fname;
	private String lname;
	private int id;
	
	@Id
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}

}
