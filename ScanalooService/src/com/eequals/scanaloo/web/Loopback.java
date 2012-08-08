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

@WebServlet(name="loopback", urlPatterns={"/loopback"})
public class Loopback extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException {
		long loop_id = new Long(request.getParameter("loop_id"));
		long user_id = new Long(request.getParameter("user_id"));
		
		String sql = "select * from loop_image where loop_id = " + loop_id;
		try {
			ResultSet result = Scanaloo.db.doSelect(sql);

			if(result.next())
			{
				long img_id = result.getLong("img_id");
				String title = result.getString("title");
				
				request.setAttribute("img_id", img_id);
				request.setAttribute("title", title);
				request.setAttribute("loop_id", loop_id);
				request.setAttribute("user_id", user_id);
				request.getRequestDispatcher("/WEB-INF/loopback.jsp").forward(
						request, response);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException {
		long loop_id = new Long(request.getParameter("loop_id"));
		long user_id = new Long(request.getParameter("user_id"));
		
		int vote = new Integer(request.getParameter("vote"));
		String comment = request.getParameter("comment");
		
		String sql = "insert into loopback (loop_id, [user_id], vote, comment) " +
		"values (" + loop_id + ", " + user_id + ", " + vote + ", '" + comment + "')";
		
		Scanaloo.db.doCommand(sql);
		
		response.sendRedirect("loops?user_id=" + user_id + 
				"&type=" + Scanaloo.TYPE_LOOPS);
		
	}

}
