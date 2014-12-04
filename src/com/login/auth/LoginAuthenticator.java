package com.login.auth;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.login.connection.CloudSQLConn;

 
public class LoginAuthenticator extends HttpServlet  
{	
	private static final long serialVersionUID = 1L;
    protected void doGet(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException
    {    	
        String user=req.getParameter("userName");
        String pass=req.getParameter("userPassword");
        CloudSQLConn conn = new CloudSQLConn();
        try {
			conn.establishConnection();
		} catch (ClassNotFoundException e) {
			System.out.println("Exception occurred :" + e.getMessage());
		} catch (SQLException e) {
			System.out.println("Exception occurred :" + e.getMessage());
		}
        if(conn.isAuthUser(user, pass)){
        	conn.closeConnection();
        	RequestDispatcher rd = req.getRequestDispatcher("ResponseServlet");
        	rd.forward(req,res);

        } else {
        	res.sendRedirect("/accessDenied.html");
        }
    }
 
}