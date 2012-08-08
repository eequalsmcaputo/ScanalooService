package com.eequals.scanaloo.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoopWeb
 */
@WebServlet("/loopweb")
public class LoopWeb extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		long user_id = Long.parseLong(request.getParameter("user_id"));
		request.setAttribute("user_id", user_id);
		request.getRequestDispatcher("/WEB-INF/loopweb.jsp").forward(
				request, response);
	}

}
