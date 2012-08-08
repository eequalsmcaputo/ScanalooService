package com.eequals.scanaloo.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eequals.scanaloo.util.Scanaloo;

@WebServlet("/images")
public class Images extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException {
		
		long img_id = new Long(request.getParameter("id"));
		
		try {
			System.out.println("Image request received.");
			
			byte[] bytes = Scanaloo.db.getImage(img_id);
			
			if (bytes != null)
			{
				response.setContentType("image/jpeg");
				response.setContentLength(bytes.length);
				OutputStream out = response.getOutputStream();
				out.write(bytes);
				out.flush();   
				out.close();
				System.out.println("Image sent.");
			}
	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
