<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Edit Meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Meals Edit</h2>
<hr>
<jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
<form method="post" action="meals">
    <input type="hidden" name="id" value="${meal.id}">
    <dl>
        <dt>Date:</dt>
        <dt><input type="datetime-local" value="${meal.dateTime}" name="dateTime"></dt>
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
