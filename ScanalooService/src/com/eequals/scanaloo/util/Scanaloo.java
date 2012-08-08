package com.eequals.scanaloo.util;

import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.restfb.*;
import com.restfb.types.*;
import com.eequals.scanaloo.db.*;
import com.eequals.scanaloo.web.LoopItem;
import com.google.android.gcm.server.*;

public class Scanaloo {
	public static final ScanalooDB db = new ScanalooDB(true);
	
	public static final int TYPE_LOOPS = 1;
	public static final int TYPE_MYLOOPS = 2;
	
	public static final String GOOGLE_API_KEY = "AIzaSyATXNvr9ZxOUZ2mJVMPUn5DIB99YdviBTo";
	public static final String FB_APP_ID = "259350184162526";
	public static final String FB_SECRET_KEY = "861be201c7df6813187b4b32b19483fc";
	//public static final String FB_ACCESS_TOKEN = "259350184162526|6ajNXynVd1jcuGLAyA3dcNN0XJQ";
	public static final String FB_ACCESS_TOKEN_SCANALOO = "AAADr4KuZAdN4BAHN9xwLNmNfKS1cZA4Hur0umZB12VCiM5cvGrnZCgQWI69Ws5Tqt4Fqegek7u2JjeMkhrMlVTGZCCjQhGHxAS7IqRNjPGouAk0S2XV3b0ed2fZB1xIjQZD";
	
	public static void sendNotification(long user_id, String msg)
	{
		String reg_id = Scanaloo.db.getRegId(user_id);
		Sender sender = new Sender(GOOGLE_API_KEY);
		Message.Builder builder = new Message.Builder();
		builder.addData("message", msg);
		Message message = builder.build();
		try {
			Result result = sender.send(message, reg_id, 5);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void fbPostLoopOnPage(String user_name, long user_id,
			long loop_id)
	{
		FacebookClient fb = new DefaultFacebookClient(FB_ACCESS_TOKEN_SCANALOO);
		LoopItem li = Scanaloo.db.getLoop(user_id, 0, loop_id);
		
		
		FacebookType response = fb.publish(user_name + "/photos", FacebookType.class,
				BinaryAttachment.with("photo.jpg", li.getImageStream()),
				Parameter.with("message", li.from_user + " needs advice on " +
						li.title));
		
		System.out.println(response.getId());
		
		/*
		 * 				Parameter.with("link", "http://scanaloo.com/images?id=27"),
				Parameter.with("picture", "http://scanaloo.com/images?id=27"),
				Parameter.with("icon", "http://www.facebook.com/photo.php?fbid=348996348514470"),
		 */
		
	}
	
	public static long newLoop(long user_id, String title, String category, 
			String friends,	InputStream img) {
		   try {
		      CallableStatement cstmt = Scanaloo.db.getStoredProcCall(
		    		  "dbo.pr_loop_new", 4);
		      cstmt.setLong(1, user_id);
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
	
}
