<%--
  Created by IntelliJ IDEA.
  User: DANG KHOI
  Date: 9/26/2024
  Time: 8:57 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Product</title>
</head>
<body>
<div id="container">
    <h3> Edit Product </h3>
    <form action="controls?action=update_product" method="post">
        <table>
            <tr>
                <td>Product Name:</td>
                <td><input type="text" name="productName" value="${product.productName}"></td>
            </tr>
            <tr>
                <td>Product Description:</td>
                <td><input type="text" name="productDescription" value="${product.productDescription}"></td>
            </tr>
            <tr>
                <td>Unit:</td>
                <td><input type="text" name="unit" value="${product.unit}"></td>
            </tr>
            <tr>
                <td>Manufacture:</td>
                <td><input type="text" name="manufacture"></td>
            </tr>
            <tr>
                <td>Status:</td>
                <td><input type="text" name="status" value="${product.status}"></td>
                
            </tr>

        </table>
    </form>
</div>
</body>
</html>
