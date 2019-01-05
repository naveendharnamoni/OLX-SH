package com.ts.olx.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DAOUtility {


	
	public static Connection getConnection() {
		Connection connection = null;
		try {
				Class.forName("com.mysql.jdbc.Driver");
				connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/olx", "root", "root");		
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}

	public static void close(Object... args) {
		for (Object object : args) {
			if (object != null) {
				try {
					if (object instanceof Connection) {
						((Connection) object).close();
					}
					if (object instanceof Statement) {
						((Statement) object).close();
					}
					if (object instanceof PreparedStatement) {
						((PreparedStatement) object).close();
					}
					if (object instanceof CallableStatement) {
						((CallableStatement) object).close();
					}
					if (object instanceof ResultSet) {
						((ResultSet) object).close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
