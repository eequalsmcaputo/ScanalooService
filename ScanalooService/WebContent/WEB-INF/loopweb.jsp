<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form method=post action="loop?loop[user_id]=${user_id}" 
		enctype="multipart/form-data">
		Title: <input type="text" name="loop[title]"><br/>	
		Category: <input type="text" name="loop[category]"><br/>
		Photo: <input type="file" name="loop[photo]"><br/>
		<input type="submit" value="Loop!">
	</form>
</body>
</html>