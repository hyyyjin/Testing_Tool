package com.mir.webserverCheck.DB;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DbConnection {
	public java.sql.Connection con;
	public Statement stmt;

	public DbConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/board?useSSL=false", "root",
					"1129");
			stmt = con.createStatement();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
