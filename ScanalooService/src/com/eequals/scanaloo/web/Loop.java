package com.eequals.scanaloo.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.eequals.scanaloo.util.Scanaloo;

@WebServlet("/loop")
@MultipartConfig 
public class Loop extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException
	{
		PrintWriter resp = response.getWriter();
		resp.println("Hello from Scanaloo!");
	}
	
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter resp = response.getWriter();
	
		Part photo = request.getPart("loop[photo]");
		
		if(photo != null)
		{

			int user_id = Integer.parseInt(request.getParameter("loop[user_id]"));
			String title = request.getParameter("loop[title]");
			String category = request.getParameter("loop[category]");
			String friends = request.getParameter("loop[friends]");
			
			InputStream img = photo.getInputStream();

			long loop_id = newLoop(user_id, title, category, friends, img);
			
	        response.addHeader("loop_id", String.valueOf(loop_id));
	        response.addHeader("message", "Loop started successfully!");
	        response.addDateHeader("loop_start", System.currentTimeMillis());
	       
		}

	}
	
	public long newLoop(int user_id, String title, String category, String friends,
			InputStream img) {
		   try {
		      CallableStatement cstmt = Scanaloo.db.getStoredProcCall(
		    		  "dbo.pr_loop_new", 4);
		      cstmt.setInt(1, user_id);
		      cstmt.setInt(2, 1);
		      cstmt.setString(3, title);
		      cstmt.setString(4, category);
		      
		      System.out.println("Start Loop...");
		      
		      ResultSet result = cstmt.executeQuery();
		      long loop_id = 0;
		      long product_id = 0;
		      
		      if (result.next())
		      {
		    	  loop_id = result.getLong("loop_id");
		    	  product_id = result.getLong("product_id");
		      }
		      
		      cstmt.close();
		      
		      System.out.println("Loop Created.  Add Image...");
		      
		      try {
		    	  Scanaloo.db.addImage(product_id, 1, img);
		      } catch (SQLException e) {
		    	  e.printStackTrace();
		    	  return -1;
		      }
		      
		      System.out.println("Loop Started.");
		      return loop_id;
		   }
		   catch (Exception e) {
		      e.printStackTrace();
		      return -1;
		   }
	}
	
	/*
	 final String store = "C:\\Development\\Projects\\Scanaloo\\Test\\upload\\";
	 String filename = store + "photo" + System.currentTimeMillis() + ".png";

	  OutputStream out = new FileOutputStream(filename);
	  copy(img, out);
	  out.flush();
	  out.close();
	 */
	
	private static long copy(InputStream input, OutputStream output) throws IOException {
	    byte[] buffer = new byte[4096];

	    long count = 0L;
	    int n = 0;

	    while (-1 != (n = input.read(buffer))) {
	        output.write(buffer, 0, n);
	        count += n;
	    }
	    return count;
	}
}
