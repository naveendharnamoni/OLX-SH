package com.ts.olx.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList; 
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.jasper.tagplugins.jstl.core.ForEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysql.jdbc.PreparedStatement;
import com.ts.olx.dao.CategoryDAO;
import com.ts.olx.dao.DAOUtility;
import com.ts.olx.dao.ItemDAO;
import com.ts.olx.dao.ModeratorDAO;
import com.ts.olx.dao.SubCategoryDAO;
import com.ts.olx.dao.UserDAO;
import com.ts.olx.dto.Category;
import com.ts.olx.dto.Interest;
import com.ts.olx.dto.Item;
import com.ts.olx.dto.Moderator;
import com.ts.olx.dto.SubCategory;
import com.ts.olx.dto.User;
@Service
public class OLXService {
	
	@Autowired
	UserDAO userDAO;
	@Autowired
	ModeratorDAO moderatorDAO;
	@Autowired
	CategoryDAO categoryDAO;
	@Autowired
	SubCategoryDAO subCategoryDAO;
	@Autowired
	ItemDAO itemDAO;

	public boolean register(User user) {
		return userDAO.insert(user);
	}

	public boolean add(Category category) {
		return categoryDAO.insert(category);
	}

	public boolean add(SubCategory subCategory) {
		return subCategoryDAO.insert(subCategory);
	}

	public boolean unsubscribe(int userId) {
		return userDAO.delete(userId);
	}

	public boolean updatePhone(long phoneNumber, int userId) {
		return userDAO.update(phoneNumber, userId);
	}

	public boolean deleteCategory(int categoryId) {
		return categoryDAO.delete(categoryId);
	}

	public boolean deleteSubCategory(int subCategoryId) {
		return subCategoryDAO.delete(subCategoryId);
	}

	public User loginAsUser(String email, String password) {
		User user = userDAO.get(email);
		if (null != user && password.equals(user.getPassword())) {
			return user;
		}
		return null;
	}

	public Moderator loginAsModerator(String email, String password) {
		Moderator moderator = new ModeratorDAO().get(email);
		if (null != moderator && password.equals(moderator.getPassword())) {
			return moderator;
		}
		return null;
	}

	public List<Category> getCategories() {
		return categoryDAO.getCategories();
	}

	public List<Category> getCategoriesWithItems() {
		List<Category> categoriesList = categoryDAO.getCategories();
		for (Category category : categoriesList) {
			category.setItemsList(itemDAO.getItemsOfCategory(category.getId()));
		}
		return categoriesList;
	}

	public List<Category> getCategoriesWithSubCategories() {
		List<Category> categoriesList = categoryDAO.getCategories();
		for (Category category : categoriesList) {
			category.setSubCategoriesList(subCategoryDAO.getSubCategories(category.getId()));
		}
		return categoriesList;
	}

	public List<SubCategory> getSubCategoriesWithItems() {
		List<SubCategory> subCategoriesList = subCategoryDAO.getSubCategories();
		for (SubCategory subCategory : subCategoriesList) {
			subCategory.setItemsList(itemDAO.getItemsOfSubCategory(subCategory.getId()));
		}
		return subCategoriesList;
	}

	public List<SubCategory> getSubCategories() {
		return subCategoryDAO.getSubCategories();
	}

	public List<Item> getItems() {
		List<Item> itemsList = itemDAO.getItems();
		for (Item item : itemsList) {
			item.setImagesList(itemDAO.getImages(item.getId()));
		}
		return itemsList;
	}

	public List<Item> getAvailableItems() {
		List<Item> itemsList = itemDAO.getItems();
		for (Item item : itemsList) {
			if (item.isSold()) {
				itemsList.remove(item);
			}
		}
		for (Item item : itemsList) {
			item.setImagesList(itemDAO.getImages(item.getId()));
		}
		return itemsList;
	}

	public List<Item> getAvailableItemsSortedByDateDesc() {
		List<Item> itemsList = itemDAO.getItems();
		List<Item> availableItemsList = new ArrayList<Item>();

		for (Item item : itemsList) {
			if (item.isSold()) {
				itemsList.remove(item);
			}
			availableItemsList.add(item);
		}
		for (Item item : availableItemsList) {
			item.setImagesList(itemDAO.getImages(item.getId()));
		}
		Collections.sort(availableItemsList, new Comparator<Item>() {
			public int compare(Item o1, Item o2) {
				return o2.getPostedDate().compareTo(o1.getPostedDate());
			}
		});
		return availableItemsList;
	}

	public List<Item> getItemsPostedBy(int userId) {
		return itemDAO.getItemsPostedBy(userId);
	}

	public List<Interest> getItemInterests(int itemId) {
		return itemDAO.getItemInterests(itemId);
	}

	public List<Item> getItemsWithInterests() {
		List<Item> itemsSet = getItems();
		for (Item item : itemsSet) {
			item.setInterestsList(getItemInterests(item.getId()));
		}
		return itemsSet;
	}

	public boolean updatePrice(int itemId, double minPrice, double maxPrice) {
		return itemDAO.updatePrice(itemId, minPrice, maxPrice);
	}

	public boolean updateApproverId(int approverId, int itemId) {
		return itemDAO.updateApproverId(itemId, approverId);
	}

	public Item insert(Item item) {
		 System.out.println("olxService item");
		return itemDAO.insert(item);
		
	}

	public boolean saveImage(int id, String fileName) {
		 System.out.println("olxService saveImage");
		return itemDAO.saveImage(id, fileName);
		
	}
	public Interest insertBidAmount(Interest interest) {
		System.out.println(interest.getExpressedBy().getId());
		return itemDAO.insertBidAmount(interest);
		
	}
	public boolean updateBidAmount(int itemId,double bidAmount,int userId) {
		return itemDAO.updateBidAmount(itemId, bidAmount,userId);
		
	}
	

	public List<Item> getItemsWithInterestsSortedByPostedDate() {
		List<Item> itemlist=getAvailableItemsSortedByDateDesc();
		for (Item item : itemlist) {
			item.setInterestsList(itemDAO.getItemInterests(item.getId()));
			
		}
		return itemlist;
		
	}

	public List<Item> getItemsWithInterestsSortedByPostedDatePostedBy(int userId) {
		List<Item> itemList=itemDAO.getItemsPostedBy(userId);
		for (Item item : itemList) {
			item.setImagesList(itemDAO.getImages(item.getId()));
			item.setInterestsList(itemDAO.getItemInterests(item.getId()));
		}
		Collections.sort(itemList, new Comparator<Item>() {
			public int compare(Item o1, Item o2) {
				return o2.getPostedDate().compareTo(o1.getPostedDate());
			}
		});
		return itemList;
	}
	public List<Item> getItemsWithInterestByUser(int userId){
		List<Item> itemList = itemDAO.getItemsInterestedByUser(userId);
		for (Item item : itemList) {
			item.setImagesList(itemDAO.getImages(item.getId()));
			List<Interest> interestsList = new ArrayList<Interest>();
			interestsList.add(itemDAO.getInterestDetailsOfItem(userId, item.getId()));
			item.setInterestsList(interestsList);
		}
		return itemList;
	}
	public boolean updateBidStatus(int itemId,int userId,int status) {
		return itemDAO.updateBidStatus(itemId,userId,status);
		
	}
	public List<Item> getItemsWithNoApprovals(){
		List<Item> itemsList = itemDAO.getItemsWithNoApprovals();
		if(itemsList != null) {
		for (Item item : itemsList) {
			item.setImagesList(itemDAO.getImages(item.getId()));
		}
		return itemsList;
		}
		return itemsList;
	}

	public boolean ApproveItem(int itemId, int moderatorId) {
		return itemDAO.approveItemStatus(itemId,moderatorId);
	}

	public boolean RejectItem(int itemId) {
		return itemDAO.rejectItemStatus(itemId);
		
	}
	 
}