package com.ts.olx.dto;

import java.util.Set;

public class Moderator {
	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private long phoneNumber;
	private boolean deleted;
	private boolean admin;
	

	private Set<Category> categoriesSet;
	private Set<SubCategory> subCategoriesSet;

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

	public long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public Set<Category> getCategoriesSet() {
		return categoriesSet;
	}

	public void setCategoriesSet(Set<Category> categoriesSet) {
		this.categoriesSet = categoriesSet;
	}

	public Set<SubCategory> getSubCategoriesSet() {
		return subCategoriesSet;
	}

	public void setSubCategoriesSet(Set<SubCategory> subCategoriesSet) {
		this.subCategoriesSet = subCategoriesSet;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	@Override
	public String toString() {
		return "Moderator [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", password=" + password + ", phoneNumber=" + phoneNumber + ", deleted=" + deleted + ", admin="
				+ admin + ", categoriesSet=" + categoriesSet + ", subCategoriesSet=" + subCategoriesSet + "]";
	}

	
}
