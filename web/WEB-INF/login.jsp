<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
    </head>
    <body>
        <h1>Login</h1>
        <form method="POST" action="login">
            <label>Email: </label>
            <input name="email" type="email"><br>
            <label>Password: </label>
            <input name="password" type="password"><br>
            <input type="submit" value="Submit">
        </form>
        <a href="">Forgot password?</a>
        <a href="signup">Sign up</a>
        <c:if test="${message != null}">
            <script>
                alert("${message}");
            </script>
        </c:if>

    </body>
</html>
