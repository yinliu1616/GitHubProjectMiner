package edu.wm.cs.gitprojectminer.sql;
//STEP 1. Import required packages
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLConnection {
	// JDBC driver name and database URL
	   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	   static final String DB_URL = "jdbc:mysql://localhost/test";
	//  Database credentials
	   static final String USER = "root";
	   static final String PASS = "521616";
	   
	   
	   private Connection conn = null;
	   private Statement stmt = null;	   
	   private ResultSet rs = null;
	   private PreparedStatement prepStmt = null;
	   
	   
	   
	/**
	 * @return the conn
	 */
	public Connection getConn() {
		return conn;
	}

	/**
	 * @return the stmt
	 */
	public Statement getStmt() {
		return stmt;
	}

	/**
	 * @return the rs
	 */
	public ResultSet getRs() {
		return rs;
	}
	

	
	/**
	 * @param prepStmt the prepStmt to set
	 */
	public void setPrepStmt(PreparedStatement prepStmt) {
		this.prepStmt = prepStmt;
	}

	public MySQLConnection() {
		// TODO Auto-generated constructor stub
	}
	
	public void openConnection(){
		//STEP 2: Register JDBC driver
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
		} catch (ClassNotFoundException e) {
			System.out.println("Failed to register JDBC driver");
			e.printStackTrace();
		}

		//STEP 3: Open a connection
		System.out.println("Connecting to database...");
		try {
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
		} catch (SQLException e) {
			System.out.println("Failed to open a connection");
			e.printStackTrace();
		}
		//STEP 4: Execute a query
		System.out.println("Creating statement...");
		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			System.out.println("Failed to create statement");
			e.printStackTrace();
		}
	
	}
	  // Close database connections
	public void closeConnection(){

	    try{
	    	if (rs != null){
	    		rs.close();
	    	}
	    	if(stmt != null){
	    		stmt.close();
	    	}
	    	conn.close();
	    }catch (SQLException e){
		    System.out.println("Failed to close connections:\n");
		    e.printStackTrace();
		}      
	}
	
	
	
}
