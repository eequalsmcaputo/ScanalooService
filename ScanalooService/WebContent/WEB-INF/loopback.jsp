<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style type="text/css">
img {
width:100%;
}
</style>
<title>Loopback</title>
</head>
<body>
	<img src="${pageContext.request.contextPath}/images?id=${img_id}"/>
	<h3>What do you think of ${title}?</h3>
	<form name="loop_back" 
	action="${pageContext.request.contextPath}/loopback?loop_id=${loop_id}&user_id=${user_id}" 
	method="post">
		<input type="radio" name="vote" value="1" /> Buy<br/>
		<input type="radio" name="vote" value="0" /> Don't Buy
		<input type="submit" value="Loop Back!"/>
		
		<h4>Comments (optional):</h4>
		<textarea name="comment" rows="10" cols="30"></textarea>
	</form>
</body>
</html>