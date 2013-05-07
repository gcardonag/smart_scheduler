package database;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBTest {

	/**
	 * @param args
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException {
		System.out.println("-------- PostgreSQL "
				+ "JDBC Connection Testing ------------");
 
		try {
 
			Class.forName("org.postgresql.Driver");
 
		} catch (ClassNotFoundException e) {
 
			System.out.println("Where is your PostgreSQL JDBC Driver? "
					+ "Include in your library path!");
			e.printStackTrace();
			return;
 
		}
 
		System.out.println("PostgreSQL JDBC Driver Registered!");
 
		Connection connection = null;
 
		try {
 
			connection = DriverManager.getConnection(
					//"jdbc:postgresql://127.0.0.1:5432/testdb", "mkyong",
					"jdbc:postgresql://127.0.0.1:8888/SmartScheduler", "postgres",
					"smartscheduler");
 
		} catch (SQLException e) {
 
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
 
		}
 
		if (connection != null) {
			System.out.println("You made it, take control your database now!");
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM \"Events\" WHERE id = 2");
			while (rs.next()) {
			    System.out.print("Column 1 returned ");
			    System.out.println(rs.getString(1));
			    System.out.println(rs.getString(2));
			    System.out.println(rs.getString(3));
			}
			rs.close();
			st.close();
			
			String foovalue = null;
			PreparedStatement st2 = connection.prepareStatement("INSERT INTO \"Events\" VALUES (DEFAULT,?, 'class')");
			st2.setString(1, foovalue);
			st2.executeUpdate();
			System.out.println("Added stuff.");
			st2.close();
		} else {
			System.out.println("Failed to make connection!");
		}

	}
}
