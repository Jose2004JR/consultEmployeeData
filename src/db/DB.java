package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
	public static Connection conn = null;
	
	public static Connection getConnection() {
		
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/employees?user=root&password=1234");
			
		}catch(SQLException e) {
			System.out.println("ERROR: " + e.getMessage());
		}
		return conn;
	}
}
