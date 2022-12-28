<%-- 
    Document   : signup
    Created on : Dec. 26, 2022, 11:30:39 a.m.
    Author     : debor
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sign-up Page</title>
    </head>
    <body>
        <h1>Nice to meet you!</h1>
        <br>
        <h3>Sign-up</h3>
        
        <form method="POST" action="signup">
            <label>Email: </label>
            <input type="email" name="email"><br>
            <label>Password: </label>
            <input type="password" name="password"><br>
            <label>First name: </label>
            <input name="fname"><br>
            <label>Last name: </label>
            <input name="lname"><br>
            <br>
            <input type="submit" value="Sign-up">
        </form>
    </body>
</html>
