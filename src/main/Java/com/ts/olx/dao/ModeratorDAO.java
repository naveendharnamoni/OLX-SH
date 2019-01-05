package com.ts.olx.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

import com.ts.olx.dto.Moderator;
@Component
public class ModeratorDAO {

	public Moderator get(String email){		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Moderator moderator = null;
		try {
			connection = DAOUtility.getConnection();
			preparedStatement = connection.prepareStatement("select id,first_name,last_name,phone_number,email,aes_decrypt(password,'secret'),admin,deleted from moderator where email  = ?");
			preparedStatement.setString(1, email);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				moderator = new Moderator();
				moderator.setId(resultSet.getInt(1));
				moderator.setFirstName(resultSet.getString(2));
				moderator.setLastName(resultSet.getString(3));
				moderator.setPhoneNumber(resultSet.getLong(4));
				moderator.setEmail(resultSet.getString(5));
				moderator.setPassword(resultSet.getString(6));
				if (resultSet.getInt(7)!=0) {
					moderator.setAdmin(true);
				} else {
					moderator.setAdmin(false);
				}
				if (resultSet.getInt(8) != 0) {
					moderator.setDeleted(true);
				} else {
					moderator.setDeleted(false);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return moderator;
	}

	public Moderator get(int moderatorId){		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Moderator moderator = null;
		try {
			connection = DAOUtility.getConnection();
			preparedStatement = connection.prepareStatement("select id,first_name,last_name,phone_number,email,aes_decrypt(password,'secret'),admin,deleted  from moderator where id  = ?");
			preparedStatement.setInt(1, moderatorId);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				moderator = new Moderator();
				moderator.setId(resultSet.getInt(1));
				moderator.setFirstName(resultSet.getString(2));
				moderator.setLastName(resultSet.getString(3));
				moderator.setPhoneNumber(resultSet.getLong(4));
				moderator.setEmail(resultSet.getString(5));
				moderator.setPassword(resultSet.getString(6));
				if (resultSet.getInt(7)!=0) {
					moderator.setAdmin(true);
				} else {
					moderator.setAdmin(false);
				}
				if (resultSet.getInt(8) != 0) {
					moderator.setDeleted(true);
				} else {
					moderator.setDeleted(false);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return moderator;
	}

}

