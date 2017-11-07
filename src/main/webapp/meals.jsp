<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="WEB-INF/tlds/dateTimeTag.tld" prefix="sdo"%>
<html>
<head>
<title>Meals</title>
</head>
<body>
	<h3>
		<a href="index.html">Home</a>
	</h3>
	<h2>Meals</h2>
	<table>
		<tr>
			<th>Description</th>
			<th>Date</th>
			<th>Calories</th>
			<th></th>
			<th></th>
		</tr>
		<c:forEach var="meal" items="${mealsList}">
			<tr>
				<td><font color=${meal.exceed ?  "red": "green"}> <c:out
							value="${meal.description}" />
				</font></td>
				<td><font color=${meal.exceed ?  "red": "green"}> <sdo:dateTimeTag
							date="${meal.dateTime}" />
				</font></td>
				<td><font color=${meal.exceed ?  "red": "green"}> <c:out
							value="${meal.calories}" />
				</font></td>
				<td><a href="/topjava/edit?id=${meal.id}">Edit</a></td>
				<td><a href="/topjava/delete?id=${meal.id}">Remove</a></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>