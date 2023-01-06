<%-- 
    Document   : nutrition
    Created on : Jan. 3, 2023, 9:42:23 p.m.
    Author     : debor
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Nutrition Page</title>
    </head>
    <body>
        <h1>Nutrition</h1>
        <form method="GET" action="nutrition">
            <label>Date: </label>
            <input type="date" name="date">
            <input type="submit" value="Search">
        </form>
        <table>
            <tr>
                <th>Food</th>
                <th>Servings</th>
            </tr>
            <c:forEach items="${foodRecords}" var="foodRecord">
                <tr>
                    <td>${foodRecord.foodDescription}</td>
                    <td>${foodRecord.servings}</td>
                </tr>
            </c:forEach>
        </table>
        <table>
            <tr>
                <th>Nutrient</th>
                <th>Amount</th>
            </tr>
            <c:forEach items="${nutrients}" var="nutrient">
                <tr>
                    <td>${nutrient.name}</td>
                    <td>${nutrient.value}</td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>
