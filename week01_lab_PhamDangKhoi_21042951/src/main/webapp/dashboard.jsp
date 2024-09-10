<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<html>
<head>
    <title>Account Manager</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
        }

        th, td {
            padding: 8px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }

        tr:hover {
            background-color: #f5f5f5;
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
<h1>Account Manager</h1>
<p>Role: ${role}</p>
<p>Welcome: ${account.accountId}</p>
<table border="1">
    <thead>
    <tr>
        <th>Account ID</th>
        <th>Full Name</th>
        <th>Email</th>
        <th>Phone</th>
        <th>Status</th>
        <th>Role</th>
        <th>Action</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="entry" items="${List_Account}">
        <c:set var="account" value="${entry.key}"/>
        <c:set var="roleId" value="${entry.value}"/>
        <tr>
            <td>${account.accountId}</td>
            <td>${account.fullName}</td>
            <td>${account.email}</td>
            <td>${account.phone}</td>
            <td>
                <c:choose>
                    <c:when test="${account.status == 1}">
                        Active
                    </c:when>
                    <c:otherwise>
                        Deactive
                    </c:otherwise>
                </c:choose>
            </td>
            <td>
                <c:out value="${roleId}"/>
            </td>
            <td>
                <form action="dashboard" method="post" style="display:inline;">
                    <input type="hidden" name="action" value="edit">
                    <input type="hidden" name="accountId" value="${account.accountId}">
                    <button type="submit">Edit</button>
                </form>
                <form action="dashboard" method="post" style="display:inline;"
                      onsubmit="event.preventDefault(); confirmAction('Are you sure to delete this account?', this)">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="accountId" value="${account.accountId}">
                    <button type="submit" ${enable}>Delete</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<form action="dashboard" method="post">
    <h3>Show one role</h3>
    <input type="hidden" name="action" value="show">
    <c:forEach var="role" items="${List}">
        <input type="radio" name="role" value="${role.roleId}">${role.roleName}
    </c:forEach>
    <input type="radio" name="role" value="">Hủy
    <button type="submit">Show</button>
</form>

<form action="dashboard" method="post">
    <input type="hidden" name="action" value="showAddAccountPage">
    <button ${enable}>
        Add
    </button>
</form>

<form action="logout" method="post"
      onsubmit="event.preventDefault(); confirmAction('Are you sure to logout?', this)">
    <input type="hidden" name="action" value="logout">
    <button type="submit">Logout</button>
</form>
</body>
</html>