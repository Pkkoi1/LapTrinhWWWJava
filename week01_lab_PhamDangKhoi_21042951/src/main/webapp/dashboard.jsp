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

        function toggleForm() {
            var form = document.getElementById("addRoleForm");
            var button = document.getElementById("toggleButton");
            if (form.style.display === "none") {
                form.style.display = "block";
                button.textContent = "-";
            } else {
                form.style.display = "none";
                button.textContent = "+";
                form.reset();
            }
        }

        function closeForm() {
            var form = document.getElementById("updateRoleForm");
            form.style.display = "none";
            form.reset();
        }

        window.onload = function () {
            var roleId = '<c:out value="${param.roleId}"/>';
            if (roleId) {
                document.getElementById("updateRoleForm").style.display = "block";
                document.getElementById("updateRoleId").value = roleId;
                document.getElementById("updateRoleName").value = '<c:out value="${roleUpdate.roleName}"/>';
                document.getElementById("updateDescription").value = '<c:out value="${roleUpdate.description}"/>';
                if ('<c:out value="${roleUpdate.status}"/>' === '1') {
                    document.getElementById("updateActive").checked = true;
                } else {
                    document.getElementById("updateDeactive").checked = true;
                }
            }
        };
    </script>
</head>
<body>
<div>
    <div>
        <h1>Account Manager</h1>
        <p>Role: ${role}</p>
        <p>Welcome: ${account.fullName}</p>
    </div>
    <form action="logout" method="post"
          onsubmit="event.preventDefault(); confirmAction('Are you sure to logout?', this)">
        <input type="hidden" name="action" value="logout">
        <button type="submit">Logout</button>
    </form>
</div>


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
    <input type="hidden" name="action" value="showAddAccountPage">
    <button ${enable}>
        Add
    </button>
</form>

<form action="dashboard" method="post">
    <h3>Show one role</h3>
    <input type="hidden" name="action" value="show">
    <c:forEach var="role" items="${List}">
        <input type="radio" name="role" value="${role.roleId}">${role.roleName}
    </c:forEach>
    <input type="radio" name="role" value="">Hủy
    <button type="submit">Show</button>
</form>

<h3>Role Manager</h3>
<table>
    <thead>
    <tr>
        <th>Role ID</th>
        <th>Role Name</th>
        <th>Role Description</th>
        <th>Status</th>
        <th>Action</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="role" items="${List}">
        <tr>
            <td>${role.roleId}</td>
            <td>${role.roleName}</td>
            <td>${role.description}</td>
            <td>
                <c:choose>
                <c:when test="${role.status == 1}">
                Active
                </c:when>
                <c:otherwise>
                Deactive
                </c:otherwise>
                </c:choose>
            <td>
                <form action="dashboard" method="post" style="display:inline;">
                    <input type="hidden" name="action" value="editRole">
                    <input type="hidden" name="roleId" value="${role.roleId}">
                    <button type="submit">Edit</button>
                </form>
                <form action="dashboard" method="post" style="display:inline;"
                      onsubmit="event.preventDefault(); confirmAction('Are you sure to delete this role?', this)">
                    <input type="hidden" name="action" value="deleteRole">
                    <input type="hidden" name="roleId" value="${role.roleId}">
                    <button type="submit" ${enable}>Delete</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<form action="dashboard" method="post">
    <input type="hidden" name="action" value="showAddRole">
    <button type="button" id="toggleButton" onclick="toggleForm()" ${enable}>
        +
    </button>
</form>

<form id="addRoleForm" action="dashboard" method="post" style="display: none">
    <h3>Add Roles</h3>

    <input type="hidden" name="action" value="addRole">
    <input type="text" name="roleId" placeholder="Role Id">
    <input type="text" name="roleName" placeholder="Role Name">
    <input type="text" name="description" placeholder="Description">
    <input type="radio" name="status" value="1" checked>Active
    <input type="radio" name="status" value="0">Deactive
    <button type="submit">Add</button>
</form>

<form id="updateRoleForm" action="dashboard" method="post" style="display: none">
    <h3>Update Roles</h3>

    <input type="hidden" name="action" value="updateRole">
    <input type="text" name="roleIdUpdate" placeholder="Role Id" id="updateRoleId">
    <input type="text" name="roleNameUpdate" placeholder="Role Name" id="updateRoleName">
    <input type="text" name="descriptionUpdate" placeholder="Description" id="updateDescription">
    <input type="radio" name="statusUpdate" value="1" id="updateActive">Active
    <input type="radio" name="statusUpdate" value="0" id="updateDeactive">Deactive
    <input type="radio" name="statusUpdate" value="-1" id="deleteRoleUpdate">Delete

    <button type="submit">Confirm</button>
    <button type="button" onclick="closeForm()">Cancel</button>
</form>

</body>
</html>