<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="vn.edu.iuh.fit.entities.Account" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Welcome</title>
    <style>
        div {
            margin-bottom: 10px;
        }
    </style>
    <script>
        function confirmAction(message, form) {
            if (confirm(message)) {
                form.submit();
            }
        }
    </script>
</head>
<body>
<h1>Update Infomation</h1>
<p>Welcome ${username}</p>
<p>Role: ${role}</p>
<h2>Account Information</h2>
<form action="update" method="post"
      onsubmit="event.preventDefault(); confirmAction('Are you sure to update this account\'s infomation?', this)">
    <input type="hidden" name="action" value="update">

    <div>
        <label>Account Name</label>
        <input type="text" name="accountId" value="${account.accountId}"> </input>
    </div>
    <div>
        <label>User Name</label>
        <input type="text" name="fullName" value="${account.fullName}"> </input>
    </div>
    <div>
        <label>Password</label>
        <input type="text" name="password" value="${account.password}"> </input>
    </div>
    <div>
        <label>Email</label>
        <input type="text" name="email" value="${account.email}"> </input>
    </div>
    <div>
        <label>Phone number</label>
        <input type="text" name="phone" value="${account.phone}"> </input>
    </div>
    <div>
        <label>Status</label>
        <c:choose>
            <c:when test="${account.status == 1}">
                <input type="radio" name="status" value="1" checked ${enable}> Active
                <input type="radio" name="status" value="0" ${enable}> Deactive
                <input type="radio" name="status" value="-1" ${enable}> Xóa
            </c:when>
            <c:when test="${account.status == 0}">
                <input type="radio" name="status" value="1" ${enable}> Active
                <input type="radio" name="status" value="0" checked ${enable}> Deactive
                <input type="radio" name="status" value="-1" ${enable}> Xóa
            </c:when>
            <c:when test="${account.status == -1}">
                <input type="radio" name="status" value="1" ${enable}> Active
                <input type="radio" name="status" value="0" ${enable}> Deactive
                <input type="radio" name="status" value="-1" checked ${enable}> Xóa
            </c:when>
        </c:choose>
    </div>
    <div>
        <label>Role</label>
        <c:choose>
            <c:when test="${userRole == 'admin'}">
                <input type="radio" name="role" value="admin" checked ${enable}> Admin
                <input type="radio" name="role" value="user" ${enable}> User
            </c:when>
            <c:when test="${userRole == 'user'}">
                <input type="radio" name="role" value="admin" ${enable}> Admin
                <input type="radio" name="role" value="user" checked ${enable}> User
            </c:when>
        </c:choose>
    </div>
    <button type="submit">Update</button>
</form>

<form action="logout" method="post"
      onsubmit="event.preventDefault(); confirmAction('Are you sure to logout?', this)">
    <input type="hidden" name="action" value="logout">
    <button type="submit">Logout</button>
</form>
</body>
</html>