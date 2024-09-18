<%--
  Created by IntelliJ IDEA.
  User: DANG KHOI
  Date: 9/18/2024
  Time: 4:58 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Employee</title>
    <style>
        #container {
            width: 100vh;
            margin: 1% 10% 0 10%;
            justify-content: center;
            align-items: center;
            flex-direction: column;
        }
    </style>
</head>
<body>
<div id="container">
    <h3>Insert new emplyee</h3>
    <form action="controls?action=insert_employee" method="post">

        <table>
            <tr>
                <td>Full Name:</td>
                <td><input type="text" name="fullName"></td>
            </tr>
            <tr>
                <td>Birth of date:</td>
                <td><input type="date" name="dob"></td>
            </tr>
            <tr>
                <td>Email:</td>
                <td><input type="text" name="email"></td>
            </tr>
            <tr>
                <td>Phone:</td>
                <td><input type="text" name="phone"></td>
            </tr>
            <tr>
                <td>Address:</td>
                <td><input type="text" name="address"></td>
            </tr>
            <tr>
                <td>Status:</td>
                <td>
                    <select name="status">
                        <option label="ACTIVE">ACTIVE</option>
                        <option label="REST">REST</option>
                        <option label="QUIT">QUIT</option>
                    </select>
                </td>

            </tr>
            <tr>
                <td><input type="submit" value="Insert"></td>
                <td><input type="reset" value="Reset"></td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
