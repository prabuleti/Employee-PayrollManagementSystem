package com.payroll;

import java.sql.*;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/payroll_db";
    private static final String USER = "root";
    private static final String PASSWORD = "nahdraVareeV@7892";

    public static final Connection getConnection() throws SQLException {
    	Connection connection = null;
		try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		connection = DriverManager.getConnection(URL, USER, PASSWORD);
		}
		catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return connection;
    }
}
