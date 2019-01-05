package com.ts.olx.dto;

import java.util.Date;
import java.util.List;

public class Item implements Comparable<Item> {
	private int id;
	private String description;
	private double minPrice;
	private double maxPrice;
	private String locality;
	private String city;
	private String state;
	private String country;
	private int postalCode;
	private Date postedDate;
	private boolean sold;

	private List<String> imagesList;

	private User postedBy;
	private Moderator approvedBy;
	private SubCategory subCategory;

	private List<Interest> interestsList;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(double minPrice) {
		this.minPrice = minPrice;
	}

	public double getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(double maxPrice) {
		this.maxPrice = maxPrice;
	}

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(int postalCode) {
		this.postalCode = postalCode;
	}

	public Date getPostedDate() {
		return postedDate;
	}

	public void setPostedDate(Date postedDate) {
		this.postedDate = postedDate;
	}

	public boolean isSold() {
		return sold;
	}

	public void setSold(boolean sold) {
		this.sold = sold;
	}

	

	

	public List<String> getImagesList() {
		return imagesList;
	}

	public void setImagesList(List<String> imagesList) {
		this.imagesList = imagesList;
	}

	public User getPostedBy() {
		return postedBy;
	}

	public void setPostedBy(User postedBy) {
		this.postedBy = postedBy;
	}

	public Moderator getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(Moderator approvedBy) {
		this.approvedBy = approvedBy;
	}

	public SubCategory getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(SubCategory subCategory) {
		this.subCategory = subCategory;
	}

	

	@Override
	public String toString() {
		return "Item [id=" + id + ", description=" + description + ", minPrice=" + minPrice + ", maxPrice=" + maxPrice
				+ ", locality=" + locality + ", city=" + city + ", state=" + state + ", country=" + country
				+ ", postalCode=" + postalCode + ", postedDate=" + postedDate + ", sold=" + sold + ", imagesList="
				+ imagesList + ", postedBy=" + postedBy + ", approvedBy=" + approvedBy + ", subCategory=" + subCategory
				+ ", interestsList=" + interestsList + "]";
	}

	public List<Interest> getInterestsList() {
		return interestsList;
	}

	public void setInterestsList(List<Interest> interestsList) {
		this.interestsList = interestsList;
	}

	public int compareTo(Item o) {
		return this.id - o.getId();
	}

}
