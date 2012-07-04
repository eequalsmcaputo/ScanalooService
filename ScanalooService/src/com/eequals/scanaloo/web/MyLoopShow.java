package com.eequals.scanaloo.web;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eequals.scanaloo.util.Scanaloo;

@WebServlet("/myloopshow")
public class MyLoopShow extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException {
		long loop_id = new Long(request.getParameter("loop_id"));
		long user_id = new Long(request.getParameter("user_id"));
		
		
		
		String sql = "select * from loop_info where loop_id = " + loop_id;
		
		try {
			ResultSet loop = Scanaloo.db.doSelect(sql);
			LoopItem loopitem = null;
			
			if (loop.next())
			{
				loopitem = new LoopItem(user_id, Scanaloo.TYPE_MYLOOPS, loop);
				
				String sql2 = "select * from loopback_info " + 
				"where loop_id = " + loop_id;
				ResultSet loopbacks = Scanaloo.db.doSelect(sql2);
				
				ArrayList<MyLoopbackItem> loopbackitems = 
					new ArrayList<MyLoopbackItem>();
				while(loopbacks.next())
				{
					loopbackitems.add(new MyLoopbackItem(user_id, loopbacks));
				}
				
				// TODO figure out how to call properties/methods in JSP
				// without being inside a foreach
				//request.setAttribute("loopitem", loopitem);
				
				request.setAttribute("img_id", loopitem.image_id);
				request.setAttribute("title", loopitem.title);
				request.setAttribute("category", loopitem.category);
				request.setAttribute("loop_age", loopitem.loop_age);
				request.setAttribute("loopback_cnt", loopitem.loopback_cnt);
				
				request.setAttribute("loopbackitems", loopbackitems);
				
				request.getRequestDispatcher("/WEB-INF/myloopshow.jsp").forward(
						request, response);	
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
