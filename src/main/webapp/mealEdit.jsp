<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
<html>
<head>
    <title>
        <c:set var="pageTitle" value="${empty meal.id ? 'Add Meal' : 'Edit Meal'}"/>
        ${pageTitle}
    </title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>${pageTitle}</h2>
<hr>
<form method="post" action="meals">
    <input type="hidden" name="id" value="${meal.id}">
    <dl>
        <dt>Date:</dt>
        <dt><input type="datetime-local" value="${meal.dateTime.format(formatter)}" name="dateTime"></dt>
    </dl>
    <dl>
        <dt>Description:</dt>
        <dt><input type="text" value="${meal.description}" name="description"></dt>
    </dl>
    <dl>
        <dt>Calories:</dt>
        <dt><input type="number" value="${meal.calories}" name="calories"></dt>
    </dl>
    <button type="submit">Save</button>
    <button onclick="window.history.back()" type="button">Cancel</button>
</form>
</body>
</html>
