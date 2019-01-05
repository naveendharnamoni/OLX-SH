package com.ts.olx.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ts.olx.dto.User;
import com.ts.olx.util.DateUtility;
@Component
public class UserDAO {

	public boolean insert(User user) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = DAOUtility.getConnection();
			preparedStatement = connection.prepareStatement(
					"insert into user(first_name,last_name,phone_number,email,password,dob,deleted) values(?,?,?,?,aes_encrypt(?,'secret'),?,0)");
			preparedStatement.setString(1, user.getFirstName());
			preparedStatement.setString(2, user.getLastName());
			preparedStatement.setLong(3, user.getPhoneNumber());
			preparedStatement.setString(4, user.getEmail());
			preparedStatement.setString(5, user.getPassword());
			preparedStatement.setDate(6, new java.sql.Date(new DateUtility().toDate(user.getDateOfBirth()).getTime()));
			if (preparedStatement.executeUpdate() > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean delete(int userId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = DAOUtility.getConnection();
			preparedStatement = connection.prepareStatement("update user set deleted = 1 where id = ?");
			preparedStatement.setInt(1, userId);
			if (preparedStatement.executeUpdate() > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public User get(int userId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		User user = null;
		try {
			connection = DAOUtility.getConnection();
			preparedStatement = connection.prepareStatement("select id,first_name,last_name,phone_number,email,aes_decrypt(password,'secret'),dob,deleted from user where id = ?");
			preparedStatement.setInt(1, userId);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				user = new User();
				user.setId(resultSet.getInt(1));
				user.setFirstName(resultSet.getString(2));
				user.setLastName(resultSet.getString(3));
				user.setPhoneNumber(resultSet.getLong(4));
				user.setEmail(resultSet.getString(5));
				user.setPassword(resultSet.getString(6));
				user.setDateOfBirth(String.valueOf(resultSet.getDate(7)));
				if (resultSet.getInt(8) != 0) {
					user.setDeleted(true);
				} else {
					user.setDeleted(false);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	public User get(String email) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		User user = null;
		try {
			connection = DAOUtility.getConnection();
			preparedStatement = connection.prepareStatement("select id,first_name,last_name,phone_number,email,aes_decrypt(password,'secret'),dob,deleted from user where email = ?");
			preparedStatement.setString(1, email);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				user = new User();
				user.setId(resultSet.getInt(1));
				user.setFirstName(resultSet.getString(2));
				user.setLastName(resultSet.getString(3));
				user.setPhoneNumber(resultSet.getLong(4));
				user.setEmail(resultSet.getString(5));
				user.setPassword(resultSet.getString(6));
				user.setDateOfBirth(String.valueOf(resultSet.getDate(7)));
				if (resultSet.getInt(8) != 0) {
					user.setDeleted(true);
				} else {
					user.setDeleted(false);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	public List<User> getUsers() {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<User> usersList = null;
		try {
			connection = DAOUtility.getConnection();
			preparedStatement = connection.prepareStatement("select id,first_name,last_name,phone_number,email,aes_decrypt(password,'secret'),dob,deleted from user");
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				usersList = new ArrayList<User>();
				do {
					User user = new User();
					user.setId(resultSet.getInt(1));
					user.setFirstName(resultSet.getString(2));
					user.setLastName(resultSet.getString(3));
					user.setPhoneNumber(resultSet.getLong(4));
					user.setEmail(resultSet.getString(5));
					user.setPassword(resultSet.getString(6));
					user.setDateOfBirth(String.valueOf(resultSet.getDate(7)));
					if (resultSet.getInt(8) != 0) {
						user.setDeleted(true);
					} else {
						user.setDeleted(false);
					}
					usersList.add(user);
				} while (resultSet.next());

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return usersList;
	}

	public boolean update(long phoneNumber,int userId){
		Connection connection = null;
		PreparedStatement preparedStatement = null;		
		try {
			connection = DAOUtility.getConnection();
			preparedStatement = connection.prepareStatement("update user set phone_number = ? where id = ?");
			preparedStatement.setLong(1, phoneNumber);
			preparedStatement.setInt(2, userId);
			if(preparedStatement.executeUpdate() > 0){
				return true;
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return false;
	}
	
	
	
}
