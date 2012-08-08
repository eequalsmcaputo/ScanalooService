package com.eequals.scanaloo.web;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eequals.scanaloo.util.Scanaloo;

@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException {
		String user_name = request.getParameter("user_name");
		String test = request.getParameter("test");
		String first_name = "";
		
		long user_id = -1l;
		
		String sql = "select [id] [user_id], first_name from [user] where [user_name] = '" + 
		user_name + "'";
		
		ResultSet result;
		try {
			result = Scanaloo.db.doSelect(sql);
			
			if (result.next())
			{
				user_id = result.getLong("user_id");
				
				if(test != null)
				{
					first_name = result.getString("first_name");
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		if(test != null)
		{
			if(user_id >= 0)
			{
				request.setAttribute("name", first_name);
				Scanaloo.sendNotification(user_id, "Login successful");
				request.getRequestDispatcher("/WEB-INF/hello.jsp").forward(
						request, response);
			}
		} else {
			response.setContentType("text/plain");
			response.getWriter().print("user_id:" + user_id);
		}
				
	}
	
}
