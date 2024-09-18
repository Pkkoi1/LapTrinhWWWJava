<%--
  Created by IntelliJ IDEA.
  User: DANG KHOI
  Date: 9/17/2024
  Time: 1:41 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="edu.iuh.fit.backEnd.services.EmployeeService" %>
<%@ page import="edu.iuh.fit.backEnd.models.Employee" %>
<%@ page import="edu.iuh.fit.backEnd.enums.EmloyeeStatus" %>
<%@ page import="java.util.List" %>
<html>
<head>
    <title>Employees</title>
    <style>
        table, th, td {
            border: 1px solid black;
            border-collapse: collapse;
            /*align-content: center;*/
        }
        th, td {
            padding: 5px;
            text-align: left;
        }
        a {
            text-decoration: none;
            color: black;
        }
    </style>

    <script>
        function deleteEmployee(id) {
            var result = confirm("Are you sure you want to delete this employee?");
            if (result) {
                window.location.href = "controls?action=delete_employee&id=" + id;
            }
        }
        function activeEmployee(id) {
            var result = confirm("Are you sure you want to active this employee?");
            if (result) {
                window.location.href = "controls?action=active_employee&id=" + id;
            }
        }
    </script>
</head>
<body>
<h3>Employees</h3>
<%
    EmployeeService employeeService = new EmployeeService();
    List<Employee> employeeList = employeeService.getAll();
%>
<table width="70%" align="center" border="1">
    <thead>
        <tr>
            <th>Id</th>
            <th>Name</th>
            <th>Birth of date</th>
            <th>Email</th>
            <th>Phone</th>
            <th>Address</th>
            <th>Status</th>
            <th colspan="2"><a href="insertemployee.jsp">Insert</a> </th>
        </tr>
    </thead>
    <tbody>
        <%for(Employee employee: employeeList){
            long id = employee.getId();
            String delete_string = "";
            if(employee.getStatus().getValue() == 1){
               delete_string = "controls?action=delete_employee&id="+id;
            }else {
                delete_string = "controls?action=active_employee&id="+id;
            }
            String edit_string = "controls?action=edit_employee&id="+id;
        %>
        <tr>
            <td align="center"><%=id%></td>
            <td><%=employee.getFullName()%></td>
            <td><%=employee.getDob()%></td>
            <td><%=employee.getEmail()%></td>
            <td><%=employee.getPhone()%></td>
            <td><%=employee.getAddress()%></td>
            <td><%=employee.getStatus()%></td>
            <td><button><a href=<%=edit_string%>>Update</a></button></td>
            <td><button onclick= "window.location.href='<%=delete_string%>';"><a href=<%=delete_string%>><%= employee.getStatus().getValue() == 1 ? "Delete": "Active"%></a></button></td>
        </tr>
        <%}%>
    </tbody>
</table>
</body>
</html>
