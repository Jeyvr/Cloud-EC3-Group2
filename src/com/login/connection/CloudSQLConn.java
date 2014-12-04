package com.login.connection;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.appengine.api.utils.SystemProperty;

public class CloudSQLConn {
	
	String url = null;
	Connection conn;
	public void establishConnection() throws ClassNotFoundException, SQLException
	{
		if (SystemProperty.environment.value() ==
		          SystemProperty.Environment.Value.Production) {
		        // Load the class that provides the new "jdbc:google:mysql://" prefix.
		        Class.forName("com.mysql.jdbc.GoogleDriver");
		        url = "jdbc:google:mysql://core-guard-766:ec3database/EC3?user=root";
		      } else {
		        // Local MySQL instance to use during development.
		        Class.forName("com.mysql.jdbc.Driver");
		        url = "jdbc:mysql://173.194.110.170:3306/EC3?user=root";
		        // Alternatively, connect to a Google Cloud SQL instance using:
		        // jdbc:mysql://ip-address-of-google-cloud-sql-instance:3306/guestbook?user=root
		      }
		conn =  DriverManager.getConnection(url);
	}
	
	public boolean isAuthUser(String userName, String Password){
		try{
		String statement = "SELECT * FROM AppUsers";
        PreparedStatement stmt = conn.prepareStatement(statement);
        ResultSet rs = stmt.executeQuery();
		while(rs.next()){
		    String username = rs.getString("username");
			if((userName!=null) && username.equals(userName)){
				String password = rs.getString("password");
				if(Password != null && password.equals(Password))
					return true;
			}
		}
		} catch (SQLException e){
			System.out.println("Exception occurred :" + e.getMessage());
		}
		return false;
	}
	
	public String getUserName(){
		StringBuilder users = new StringBuilder();
		try{
			String statement = "SELECT * FROM AppUsers";
	        PreparedStatement stmt = conn.prepareStatement(statement);
	        ResultSet rs = stmt.executeQuery();
			while(rs.next()){
			    users.append(rs.getString("username") + " ");
			}
		} catch (SQLException e){
			System.out.println("Exception occurred :" + e.getMessage());
		}
		return users.toString();
	}
	
	public void closeConnection(){
		try {
			conn.close();
		} catch (SQLException e) {
			System.out.println("Exception occurred :" + e.getMessage());
		}
	}
	
	public Boolean isAdmin(String userName){
		try{
			String statement = "SELECT * FROM AdminUsers";
	        PreparedStatement stmt = conn.prepareStatement(statement);
	        ResultSet rs = stmt.executeQuery();
			while(rs.next()){
			    if(rs.getString("userName").equals(userName))
			    	return true;
			}
		} catch (SQLException e){
			System.out.println("Exception occurred :" + e.getMessage());
		}
		return false;
	}
}
