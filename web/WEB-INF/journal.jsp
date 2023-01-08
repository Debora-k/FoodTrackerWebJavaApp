<%-- 
    Document   : journal
    Created on : Dec. 21, 2022, 10:16:33 p.m.
    Author     : debor
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Journal Page</title>
    </head>
    <body>
        <a href="journal">Journal</a> | 
        <a href="nutrition">Nutrition</a> | 
        <a href="logout">Logout</a>
        <br>
        <h1>Record your food journal!</h1>
        <form method="GET" action="journal">
            <label>Search for food: </label>
            <input type="text" name="search">
            <input type="submit" value="Search">
        </form>
        <br>
        <br>
        <form method="POST" action="journal">
            <label>Food: </label>
            <select name="food">
                <c:forEach items="${foods}" var="foodjsp">
                    <option>${foodjsp}</option>
                </c:forEach>
            </select>
            <br>
            <label>Date: </label>
            <input type="date" name="date">
            <br>
            <label>Servings: </label>
            <input type="number" name="servings">
            <br>
            <input type="submit" value="Save into your journal">
        </form>
        <br>
        <br>
        <h2>My Food</h2>
        <form method="GET">
            <label>Sort By: </label>
            <select name="sort">
                <option <c:if test="${sort eq 'Date'}">selected</c:if>>Date</option>
                <option <c:if test="${sort eq 'Food'}">selected</c:if>>Food</option>
                <option <c:if test="${sort eq 'Servings'}">selected</c:if>>Servings</option>
                <option <c:if test="${sort eq 'Calories'}">selected</c:if>>Calories</option>
            </select>
            <input type="submit" value="Sort">
        </form>
        <table>
            <tr>
                <th>Date</th>
                <th>Food</th>
                <th>Servings</th>
                <th>Calories</th>
            </tr>
            <c:forEach items="${foodRecords}" var="foodRecordsjsp">
                <tr>
                    <td>${foodRecordsjsp.date}</td>
                    <td>${foodRecordsjsp.foodDescription}</td>
                    <td>${foodRecordsjsp.servings}</td>
                    <td>${foodRecordsjsp.calories}</td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>
