<%-- 
    Document   : forgotPassword
    Created on : Dec. 28, 2022, 8:15:32 p.m.
    Author     : debor
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Forgot Password Page</title>
    </head>
    <body>
        <h1>Change Password</h1>
        <form method="POST" action="changePassword">
            <label>New password: </label>
            <input name="password" type="password"><br>
            <br>
            <input name="id" type="hidden" value="${id}">
            <input type="submit" value="Change Password">
        </form>
    <c:if test="${message != null}">
        <script>
            alert("${message}");
        </script>
    </c:if>
    </body>
</html>
