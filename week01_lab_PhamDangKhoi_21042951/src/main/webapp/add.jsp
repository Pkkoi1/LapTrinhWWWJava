<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Add new Account</title>
    <style>
        div {
            margin-bottom: 10px;
        }

        label {
            display: inline-block;
            width: 150px;
        }

        input, select {
            width: 200px;
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
<h1>Add new Account</h1>
<form id="update" action="add" method="post">
    <input type="hidden" name="action" value="add">

    <div>
        <label>Account Name</label>
        <input type="text" placeholder="Account name" name="accountId"> </input>
    </div>
    <div>
        <label>Full Name</label>
        <input type="text" placeholder="Name" name="fullName"> </input>
    </div>
    <div>
        <label>Password</label>
        <input type="password" placeholder="Mat khau" name="password"> </input>
    </div>
    <div>
        <label>Email</label>
        <input type="text" placeholder="Email" name="email"> </input>
    </div>
    <div>
        <label>Phone number</label>
        <input type="text" placeholder="Phone" name="phone"> </input>
    </div>
    <div>
        <label>Role</label>
        <select name="role">
            <c:forEach var="role" items="${roleId}">
                <option value="${role.roleId}">${role.roleName}</option>
            </c:forEach>
        </select>
    </div>

    <input type="submit" value="Add">
</form>

<form action="logout" method="post"
      onsubmit="event.preventDefault(); confirmAction('Are you sure to logout?', this)">
    <input type="hidden" name="action" value="logout">
    <button type="submit">Logout</button>
</form>

</body>
</html>