<%--
  Created by IntelliJ IDEA.
  User: DANG KHOI
  Date: 9/7/2024
  Time: 8:16 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Account</title>

</head>
<body>
<form action="account" method="post">
    <input type="hidden" value="account" name="account">
    <input type="text" name="username" placeholder="Username">
    <input type="password" name="password" placeholder="Password">
    <input type="text" name="email" placeholder="email">

    <input type="submit" value="Login">
</form>
</body>
</html>
