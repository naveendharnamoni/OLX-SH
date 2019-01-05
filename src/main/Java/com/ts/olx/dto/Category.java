package com.ts.olx.dto;

import java.util.List;

public class Category implements Comparable<Category>{

	private int id;
	private String name;
	private boolean deleted;
	private Moderator addedBy;
	private String iconPath;

	private List<SubCategory> subCategoriesList;
	private List<Item> itemsList;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public Moderator getAddedBy() {
		return addedBy;
	}

	public void setAddedBy(Moderator addedBy) {
		this.addedBy = addedBy;
	}

	

	public List<SubCategory> getSubCategoriesList() {
		return subCategoriesList;
	}

	public void setSubCategoriesList(List<SubCategory> subCategoriesList) {
		this.subCategoriesList = subCategoriesList;
	}

	public List<Item> getItemsList() {
		return itemsList;
	}

	public void setItemsList(List<Item> itemsList) {
		this.itemsList = itemsList;
	}

	public String getIconPath() {
		return iconPath;
	}

	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}

	public int compareTo(Category o) {
		return this.id - o.getId();
	}

	
}
