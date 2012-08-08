package com.eequals.scanaloo.web;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MyLoopbackItem {

	public long loopback_id;
	public long user_id;
	public String user_fullname;
	public String vote_str;
	public String comment;
	public String loopback_age;
	
	public MyLoopbackItem(long User_id, long Loopback_id, String User_fullname, 
			String Vote_str, String Comment, String Loopback_age)
	{
		user_id = User_id;
		loopback_id = Loopback_id;
		user_fullname = User_fullname;
		vote_str = Vote_str;
		comment = Comment;
		loopback_age = Loopback_age;
	}
	
	public MyLoopbackItem(long User_id, ResultSet loopbacks) throws SQLException
	{
		this(User_id, 
				loopbacks.getLong("loopback_id"),
				loopbacks.getString("user_fullname"),
				loopbacks.getString("vote_str"),
				loopbacks.getString("comment"),
				loopbacks.getString("loopback_age"));
	}
	
	public String getLoopbackID(){
		return String.valueOf(loopback_id);
	}
	
	public String getUserID(){
		return String.valueOf(user_id);
	}
	
	public int childCommentCount(){
		return 0;
	}
	
	public String getDispString()
	{
		String disp = user_fullname + " voted " + vote_str;
		String comment_str = null;
		
		if (comment.length() == 0)
		{
			comment_str = " without commenting.";
		} else {
			comment_str = " and said:\n" + comment;
		}
		
		return disp + comment_str + " (" + loopback_age + ")";
	}
	
}
