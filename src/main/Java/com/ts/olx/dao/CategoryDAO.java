package com.ts.olx.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ts.olx.dto.Category;
@Component
public class CategoryDAO {

	public boolean insert(Category category) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = DAOUtility.getConnection();
			preparedStatement = connection
					.prepareStatement("insert into category(name,added_by,icon_path,deleted) values(?,?,?,0)");
			preparedStatement.setString(1, category.getName());
			preparedStatement.setInt(2, category.getAddedBy().getId());
			preparedStatement.setString(3, category.getIconPath());
			if (preparedStatement.executeUpdate() > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean delete(int categoryId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = DAOUtility.getConnection();
			preparedStatement = connection.prepareStatement("update category set deleted = 1 where id = ?");
			preparedStatement.setInt(1, categoryId);
			if (preparedStatement.executeUpdate() > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public Category get(int categoryId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Category category = null;
		try {
			connection = DAOUtility.getConnection();
			preparedStatement = connection.prepareStatement("select * from category where id = ?");
			preparedStatement.setInt(1, categoryId);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				category = new Category();
				category.setId(resultSet.getInt(1));
				category.setName(resultSet.getString(2));
				if (resultSet.getInt(4) != 0) {
					category.setDeleted(true);
				} else {
					category.setDeleted(false);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return category;
	}

	public List<Category> getCategories() {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Category> categoriesList = null;
		try {
			connection = DAOUtility.getConnection();
			preparedStatement = connection.prepareStatement("select * from category");
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				categoriesList = new ArrayList<Category>();
				do {
					Category category = new Category();
					category.setId(resultSet.getInt(1));
					category.setName(resultSet.getString(2));
					category.setIconPath(resultSet.getString(4));
					if (resultSet.getInt(5) != 0) {
						category.setDeleted(true);
					} else {
						category.setDeleted(false);
					}
					categoriesList.add(category);
				} while (resultSet.next());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return categoriesList;
	}
}
