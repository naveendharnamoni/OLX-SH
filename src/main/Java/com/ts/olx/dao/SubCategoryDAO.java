
package com.ts.olx.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ts.olx.dto.Category;
import com.ts.olx.dto.SubCategory;
@Component
public class SubCategoryDAO {

	public boolean insert(SubCategory subCategory) {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = DAOUtility.getConnection();
			preparedStatement = connection
					.prepareStatement("insert into subcategory(name,category_id,added_by,icon_path,deleted) values(?,?,?,?,0)");
			preparedStatement.setString(1, subCategory.getName());
			preparedStatement.setInt(2, subCategory.getCategory().getId());
			preparedStatement.setInt(3, subCategory.getAddedBy().getId());
			preparedStatement.setString(4, subCategory.getIconPath());
			if (preparedStatement.executeUpdate() > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean delete(int subCategoryId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = DAOUtility.getConnection();
			preparedStatement = connection.prepareStatement("update subcategory set deleted = 1 where id = ? ");
			preparedStatement.setInt(1, subCategoryId);
			if (preparedStatement.executeUpdate() > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public SubCategory get(int subCategoryId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		SubCategory subCategory = null;
		try {
			connection = DAOUtility.getConnection();
			preparedStatement = connection.prepareStatement("select id,name,category_id,added_by,icon_path,deleted from subcategory where id = ?");
			preparedStatement.setInt(1, subCategoryId);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				subCategory = new SubCategory();
				subCategory.setId(resultSet.getInt(1));
				subCategory.setName(resultSet.getString(2));
				if (resultSet.getInt(4) != 0) {
					subCategory.setDeleted(true);
				} else {
					subCategory.setDeleted(false);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return subCategory;
	}

	public List<SubCategory> getSubCategories() {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<SubCategory> subCategoriesList = null;
		try {
			connection = DAOUtility.getConnection();
			preparedStatement = connection.prepareStatement("select name,category_id,added_by,icon_path,deleted from subcategory ");
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				subCategoriesList = new ArrayList<SubCategory>();
				do {
					SubCategory subCategory = new SubCategory();
					subCategory.setId(resultSet.getInt(1));
					subCategory.setName(resultSet.getString(2));
					if (resultSet.getString(4) != null) {
						subCategory.setDeleted(true);
					} else {
						subCategory.setDeleted(false);
					}
					subCategoriesList.add(subCategory);
				} while (resultSet.next());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return subCategoriesList;
	}

	public List<SubCategory> getSubCategories(int categoryId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<SubCategory> subCategoriesList = null;
		try {
			connection = DAOUtility.getConnection();
			preparedStatement = connection.prepareStatement("select name,category_id,added_by,icon_path,deleted,id from subcategory where category_id = ? ");
			preparedStatement.setInt(1, categoryId);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				subCategoriesList = new ArrayList<SubCategory>();
				do {
					SubCategory subCategory = new SubCategory();
					subCategory.setId(resultSet.getInt(6));
					subCategory.setName(resultSet.getString(1));
					//subCategory.setCategory(resultSet.getInt(2));
					//subCategory.setAddedBy(resultSet.getInt(3));
					subCategory.setIconPath(resultSet.getString(4));
					if (resultSet.getString(4) != null) {
						subCategory.setDeleted(true);
					} else {
						subCategory.setDeleted(false);
					}
					subCategoriesList.add(subCategory);
				} while (resultSet.next());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return subCategoriesList;
	}

}