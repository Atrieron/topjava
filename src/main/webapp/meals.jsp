<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions"%>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>--%>
<html>
<head>
<title>Meal list</title>
<style>
.normal {
	color: green;
}

.exceeded {
	color: red;
}
</style>
</head>
<body>
	<section>
		<h3>
			<a href="index.html">Home</a>
		</h3>
		<form method="post" action="meals">
			<select name="userId">
				<c:if test="${currentId==1}">
					<option value="1" selected>Ivanov</option>
					<option value="2">Petrov</option>
				</c:if>
				<c:if test="${currentId==2}">
					<option value="1">Ivanov</option>
					<option value="2" selected>Petrov</option>
				</c:if>
			</select>
			<table>
				<tr>
					<th></th>
					<th>Start</th>
					<th>End</th>
				</tr>
				<tr>
					<td>Date</td>
					<td><input type="date" name="startDate" /></td>
					<td><input type="date" name="endDate" /></td>
				</tr>
				<tr>
					<td>Time</td>
					<td><input type="time" name="startTime" /></td>
					<td><input type="time" name="endTime" /></td>
				</tr>
			</table>
			<input type="submit">
		</form>
		<h2>Meals</h2>
		<a href="meals?action=create">Add Meal</a>
		<hr />
		<table border="1" cellpadding="8" cellspacing="0">
			<thead>
				<tr>
					<th>Date</th>
					<th>Description</th>
					<th>Calories</th>
					<th></th>
					<th></th>
				</tr>
			</thead>
			<c:forEach items="${meals}" var="meal">
				<jsp:useBean id="meal" scope="page"
					type="ru.javawebinar.topjava.to.MealWithExceed" />
				<tr class="${meal.exceed ? 'exceeded' : 'normal'}">
					<td>
						<%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
						<%--<%=TimeUtil.toString(meal.getDateTime())%>--%> <%--${fn:replace(meal.dateTime, 'T', ' ')}--%>
						${fn:formatDateTime(meal.dateTime)}
					</td>
					<td>${meal.description}</td>
					<td>${meal.calories}</td>
					<td><a href="meals?action=update&id=${meal.id}">Update</a></td>
					<td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
				</tr>
			</c:forEach>
		</table>
	</section>
</body>
</html>