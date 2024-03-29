<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    request.setAttribute("formatter", formatter);
%>
<html lang="ru">
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<table border="1" cellpadding="3">
    <thead>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${mealToList}" var="meal">
        <tr style="color: ${meal.isExcess() ? 'red' : 'green'};">
            <td>${meal.getDateTime().format(formatter)}</td>
            <td>${meal.getDescription()}</td>
            <td>${meal.getCalories()}</td>
        </tr>
    </c:forEach>

    </tbody>

</table>
</body>
</html>
