<%--
  Created by IntelliJ IDEA.
  User: DANG KHOI
  Date: 9/17/2024
  Time: 2:07 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="edu.iuh.fit.backEnd.services.CustomerService" %>
<%@ page import="edu.iuh.fit.backEnd.models.Customer" %>
<%@ page import="java.util.List" %>
<html>
<head>
    <title>Customer</title>
</head>
<body>
    <h3>Customer</h3>
    <%
        CustomerService customerService = new CustomerService();
        List<Customer> customerList = customerService.getAll();
    %>
    <table width="70%" align="center" border="1">
        <tr>
            <th>Id</th>
            <th>Name</th>
            <th>Email</th>
            <th>Phone</th>
            <th>Address</th>
            <th colspan="2"><a href="insertCustomer.jsp">Insert</a> </th>
        </tr>
        <%for(Customer customer: customerList){
            long id = customer.getId();
            String delete_string = "controls?action=delete_customer&id="+id;
            String edit_string = "controls?action=edit_customer&id="+id;
        %>
        <tr>
            <td align="center"><%=id%></td>
            <td><%=customer.getCustName()%></td>
            <td><%=customer.getEmail()%></td>
            <td><%=customer.getPhone()%></td>
            <td><%=customer.getAddress()%></td>
            <td><a href=<%=edit_string%>>Update</a> </td>
            <td><a href=<%=delete_string%>>Delete</a> </td>
        </tr>

        <%}%>
</body>
</html>
