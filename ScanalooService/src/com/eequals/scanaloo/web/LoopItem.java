package com.eequals.scanaloo.web;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.eequals.scanaloo.util.Scanaloo;

public class LoopItem {
	
	public long user_id;
	public int type;
	
	public int loop_id;
	public String from_user;
	public String title;
	public String category;
	public Date open_date;
	public String loop_age;
	public String loopback_cnt;
	public long image_id;
	
	public LoopItem (long User_id, int Type,
			int Loop_id, String From_user, String Title, String Category, 
			Date Open_date, String Loop_age, String Loopback_cnt, long Image_id)
	{
		user_id = User_id;
		type = Type;
		
		loop_id = Loop_id;
		from_user = From_user;
		title = Title;
		category = Category;
		open_date = Open_date;
		loop_age = Loop_age;
		loopback_cnt = Loopback_cnt;
		image_id = Image_id;
	}
	
	public LoopItem(long User_id, int Type, ResultSet loops) throws SQLException
	{
		this(User_id, Type,
				loops.getInt("loop_id"), 
				loops.getString("user_fullname"), 
				loops.getString("title"),
				loops.getString("category_string"),
				loops.getDate("open_date"),
				loops.getString("loop_age"),
				loops.getString("loopback_cnt"),
				loops.getLong("image_id"));
	}
	
	public String getUri()
	{
		switch(type)
		{
		case 1:
			return getLoopbackUri();
		case 2:
			return getMyUri();
		default:
			return "";
		}
	}
	
	public String getDispString()
	{
		switch(type)
		{
		case 1:
			return getLoopbackDispString();
		case 2:
			return getMyDispString();
		default:
			return "";
		}		
	}
	
	private String getLoopUri(String page)
	{
		return "/" + page + "?loop_id=" + loop_id + "&user_id=" + user_id;
	}
	
	private String getLoopbackUri()
	{
		return getLoopUri("loopback");
	}
	
	private String getLoopbackDispString()
	{
		return from_user + " needs your advice on " + title + "(" + 
			loop_age + ").";
	}
	
	private String getMyUri()
	{
		return getLoopUri("myloopshow");
	}
	
	private String getMyDispString()
	{
		return title + ": " + loop_age + " (" + loopback_cnt + ")";
	}
	
	public byte[] getImage()
	{
		return Scanaloo.db.getImage(image_id);
	}
	
	public InputStream getImageStream()
	{
		return new ByteArrayInputStream(getImage());
	}
	
}
