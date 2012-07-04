package com.eequals.scanaloo.db;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.sql.*;

import javax.imageio.ImageIO;

public class ScanalooDB {

	private Connection _conn;
	
	public ScanalooDB(boolean connect)
	{
		if(connect)
		{
			connect();
		}
	}
	
	public void connect()
	{
		try {
			Properties props = new Properties();
			props.load(ScanalooDB.class.getClassLoader()
				    .getResourceAsStream("config.properties"));
			
			String server = "MIKE-M17X\\SQLEXPRESS";
			//String server = "AMAZONA-JEJCBHD";
			String database = "Scanaloo";
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	        String connstr = "jdbc:sqlserver://" + server + ";" + 
            "database=" + database + ";" + 
            "user=scanaloo_svc;" + 
            "password=loop";
	        
	        //System.out.println("Connection String: " + connstr);
	        
	        _conn = DriverManager.getConnection(connstr);
	        //_conn = DriverManager.getConnection("jdbc:sqlserver:Scanaloo;user=scanaloo_svc;password=loop");
	        System.out.println("Connected to Scanaloo DB.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int doCommand(String sqlCommand) throws SQLException
	{
		Statement cmd = _conn.createStatement();
		int result = cmd.executeUpdate(sqlCommand);
		cmd.close();
		
		return result;
	}
	
	public ResultSet doSelect(String sqlSelect) throws SQLException
	{
		Statement select = _conn.createStatement();
		ResultSet result = select.executeQuery(sqlSelect);
		
		return result; 
	}
	
	public int addImage(long product_id, int img_order, InputStream img_content) 
		throws SQLException 
	{
		System.out.println("Adding Image...");
		String sql = "insert into image (product_id, img_order, img_content) " +
			"values (?, ?, ?)";
		PreparedStatement pstmt = _conn.prepareStatement(sql);
		
		pstmt.setLong(1, product_id);
		pstmt.setInt(2, img_order);
		
		
		pstmt.setBinaryStream(3, img_content);
		
		int result = pstmt.executeUpdate();
		pstmt.close();
		System.out.println("Image Added.");
		
		return result; 
	}
	
	private BufferedImage rotate(InputStream img, double degs) 
	throws IOException {
	      Image image = ImageIO.read(img);
	      int width = image.getWidth(null);
	      int height = image.getHeight(null);
	      
	      BufferedImage temp = new BufferedImage(height, width, 
	    		  BufferedImage.TYPE_INT_RGB);
	      Graphics2D g2 = temp.createGraphics();
	      g2.rotate(Math.toRadians(degs), height / 2, height / 2);
	      g2.drawImage(image, 0, 0, Color.WHITE, null);
	      g2.dispose();
	      
	      return temp;
	 }
	
	public CallableStatement getStoredProcCall(String sp_name_with_owner, 
			int param_count)
	{
		String call_str = "";
		String param_str = "?";
		
		if (param_count > 1)
		{
			for(int i = 2; i <= param_count; i++)
			{
				param_str += ", ?";
			}
		}
		
		call_str = "{call " + sp_name_with_owner + "(" + param_str + ")}";
		
		try {
			System.out.println("Prepare Call...");
			CallableStatement cstmt = _conn.prepareCall(call_str);
			return cstmt;
		} catch (SQLException e) {
			System.out.println("Prepare Call failed.");
			e.printStackTrace();
			return null;
		}
	}
	
}
