<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form action="meals" method="post">
		<c:if test="${meal!=null}">
			<input type="hidden" value="${meal.id}"	name="id" />
		</c:if>
		<p>Description</p>
		<input type="text"
			<c:if test="${meal!=null}">value="<c:out value="${meal.description}"/>"</c:if>
			name="description" /> <br>
		<p>Time</p>
		<input type=datetime-local
			<c:if test="${meal!=null}">value="<c:out value="${meal.dateTime}"/>"</c:if>
			name="dateTime" /> <br>
		<p>Calories</p>
		<input type="number"
			<c:if test="${meal!=null}">value="<c:out value="${meal.calories}"/>"</c:if>
			name="calories" /> <br> <input type="submit" />
	</form>
</body>
</html>