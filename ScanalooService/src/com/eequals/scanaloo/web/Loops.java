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

import com.eequals.scanaloo.db.ScanalooDB;
import com.eequals.scanaloo.util.Scanaloo;

/**
 * Servlet implementation class Loops
 */
@WebServlet("/loops")
public class Loops extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException {
		try {
			long user_id = new Long(request.getParameter("user_id"));
			int type = new Integer(request.getParameter("type"));
			
			String op = null;
			String order = null;
			String page_title = null;
			
			switch(type)
			{
			case 1:
				op = "<>";
				order = "desc";
				page_title = "Loops from Friends";
				break;
			case 2:
				op = "=";
				order = "asc";
				page_title = "My Loops";
				break;
			default:
				// TODO throw invalid type exception	
			}
			
			// TODO clean up sql: the "not in" will probably not perform well
			// once there are a lot of records
			String sql = "select * from loop_info li " +
			"where [status] = 1 and user_id " + op + " " + user_id + " " +
			" and loop_id not in (select loop_id from loopback where user_id = " +
			user_id + ") " +
			"order by open_date " + order;
			//System.out.println(sql);
			
			ResultSet loops = Scanaloo.db.doSelect(sql);
			
			ArrayList<LoopItem> loopitems = new ArrayList<LoopItem>();
			while(loops.next())
			{
				loopitems.add(new LoopItem(user_id, type, loops));
			}
			
			request.setAttribute("loopitems", loopitems);
			request.setAttribute("user_id", user_id);
			request.setAttribute("type", type);
			request.setAttribute("page_title", page_title);
			request.getRequestDispatcher("/WEB-INF/loops.jsp").forward(
					request, response);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
