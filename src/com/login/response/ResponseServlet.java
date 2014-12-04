package com.login.response;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.login.connection.CloudSQLConn;

public class ResponseServlet extends HttpServlet
{

	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException
	    {    
		 CloudSQLConn conn = new CloudSQLConn();
		 try {
			conn.establishConnection();
		} catch (ClassNotFoundException e) {
			System.out.println("Exception occurred :" + e.getMessage());
		} catch (SQLException e) {
			System.out.println("Exception occurred :" + e.getMessage());
		}
		 String user = req.getParameter("userName");
		 if(user == null){
			UserService userService = UserServiceFactory.getUserService();
			User userser = userService.getCurrentUser();
			user = userser.getEmail();
		 }
	     PrintWriter pw=res.getWriter();
	     res.setContentType("text/html");
	     pw.println("<h1 style=\"color:red\">Welcome : "+ user +"</h1>");  
	     if(conn.isAdmin(user)){
	     pw.println("<h2 style=\"color:blue\">Group Members:</h2>");  
	     pw.println("<p>"+conn.getUserName()+"</p>");  
	     }
	     pw.println("<h2 style=\"color:blue\">RDS Endpoint:</h2>");  
	     pw.println("<p>jdbc:google:mysql://core-guard-766:ec3database/EC3 or jdbc:mysql://173.194.110.170:3306/EC3</p>");  
	     pw.println("<h3 style=\"color:blue\">Team Members:</h3>");  
	     pw.println("<p>Asha, Naresh Kumar,Jey Vignesh,Uma</p>");  
	     pw.println("<a href=\"index.html\">Logout</a>");
	     conn.closeConnection();
    	 pw.close();
	    }
	
}
