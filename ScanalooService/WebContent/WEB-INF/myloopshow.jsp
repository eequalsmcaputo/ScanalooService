<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style type="text/css">
img {
width:100px;
height:100px;
}
</style>
<title>My Loop Show</title>
</head>
<body>
	<p>
		<img src="${pageContext.request.contextPath}/images?id=${img_id}"/>
		<i>Started: ${loop_age}</i><br/>
		<h2>${title} (${category}): ${loopback_cnt}</h2>
	</p>
		
	<table>
	    <tbody>
			<c:forEach items="${loopbackitems}" var="loopbackitem">
		    	<tr id="${loopbackitem.getLoopbackID()}">
		           	<td>${loopbackitem.getDispString()}</td>
		           	<td><button 
		           		onclick="doReply(${loopbackitem.getLoopbackID()}, ${loopbackitem.getUserID()})"
		           		>Reply</button></td>
		           	<td>
		           		<c:if test="${loopbackitem.childCommentCount() > 0}">
		           			<a href="#" onclick="showMore()">Responses</a>
		           		</c:if>
		           	</td>
		       	</tr>
		    </c:forEach>
	    </tbody>
	</table>
	<script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
	<script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jqueryui/1.8.21/jquery-ui.min.js"></script>
	<script type="text/javascript" src="scripts/looputils.js"></script>
</body>
</html>