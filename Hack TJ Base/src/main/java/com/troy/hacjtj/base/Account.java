package com.troy.hacjtj.base;

import java.util.ArrayList;
import java.util.List;

public class Account extends MyObjectImpl {

	public String username, email;
	public List<Course> courses = new ArrayList<Course>();

	public Account(long id, String username, String email) {
		super(id);
		this.username = username;
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "Account [username=" + username + ", email=" + email + "]";
	}

	
}