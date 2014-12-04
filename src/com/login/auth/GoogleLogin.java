package com.login.auth;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
 

public class GoogleLogin extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		if (user != null) {
	        	RequestDispatcher rd = req.getRequestDispatcher("ResponseServlet");
	        	try {
					rd.forward(req,resp);
				} catch (ServletException e) {
					System.out.println("Exception Occured: " + e.getMessage());
				}
 
		} else {
			resp.sendRedirect(""+userService.createLoginURL(req.getRequestURI()));
		}
	}
}