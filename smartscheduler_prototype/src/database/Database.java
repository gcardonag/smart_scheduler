package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
	private Connection connection = null;
	
	/**
	 * 
	 * @return 0 if connection failed, 1 if connection succeeded
	 */
	public int open(){
		try {
			 
			Class.forName("org.postgresql.Driver");
 
		} catch (ClassNotFoundException e) {
 
			System.out.println("Where is your PostgreSQL JDBC Driver? "
					+ "Include in your library path!");
			e.printStackTrace();
			return 0;
 
		}
 
		System.out.println("PostgreSQL JDBC Driver Registered!");
 
		connection = null;
 
		try {
 
			connection = DriverManager.getConnection(
					//"jdbc:postgresql://127.0.0.1:5432/testdb", "mkyong",
					"jdbc:postgresql://127.0.0.1:8888/SmartScheduler", "postgres",
					//"jdbc:postgresql://127.0.0.1:5432/SmartScheduler", "postgres",
					"smartscheduler");
 
		} catch (SQLException e) {
 
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return 0;
 
		}
 
		if (connection != null) {
			System.out.println("Connection Success!");
			return 1;
		} else {
			System.out.println("Failed to make connection!");
			return 0;
		}
	}
	
	public int addUser(String user)
	{
		
	}
	
	
}
