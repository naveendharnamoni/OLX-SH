package com.ts.olx.dto;

import java.util.Date;
import java.util.Set;

public class User {

	private int id;
	private String firstName;
	private String lastName;
	private long phoneNumber;
	private String email;
	private String password;
	private String dateOfBirth;
	private boolean deleted;
	
	private Set<Item> postedItemsSet;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public Set<Item> getPostedItemsSet() {
		return postedItemsSet;
	}

	public void setPostedItemsSet(Set<Item> postedItemsSet) {
		this.postedItemsSet = postedItemsSet;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", phoneNumber=" + phoneNumber
				+ ", email=" + email + ", password=" + password + ", dateOfBirth=" + dateOfBirth + ", deleted="
				+ deleted + ", postedItemsSet=" + postedItemsSet + "]";
	}

}
