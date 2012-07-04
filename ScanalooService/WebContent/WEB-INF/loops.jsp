<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>${page_title}</title>
</head>
<body>
<h2>${page_title}</h2>
<table>
    <tbody>
        <c:forEach items="${loopitems}" var="loopitem">
            <tr><td>
            	<a href="${pageContext.request.contextPath}${loopitem.getUri()}">
            		${loopitem.getDispString()}
            	</a>
            </td></tr>
        </c:forEach>
    </tbody>
</table>
</body>
</html>