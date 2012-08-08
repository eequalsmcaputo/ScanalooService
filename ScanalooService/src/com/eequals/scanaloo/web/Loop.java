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
import com.restfb.*;

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

			long user_id = Long.parseLong(request.getParameter("loop[user_id]"));
			String title = request.getParameter("loop[title]");
			String category = request.getParameter("loop[category]");
			String friends = request.getParameter("loop[friends]");
			
			InputStream img = photo.getInputStream();

			long loop_id = Scanaloo.newLoop(user_id, title, category, friends, img);
			Scanaloo.fbPostLoopOnPage("Scanaloo", user_id, loop_id);
						
	        response.addHeader("loop_id", String.valueOf(loop_id));
	        response.addHeader("message", "Loop started successfully!");
	        response.addDateHeader("loop_start", System.currentTimeMillis());
	       
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
