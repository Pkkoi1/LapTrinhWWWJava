<%--
  Created by IntelliJ IDEA.
  User: DANG KHOI
  Date: 9/17/2024
  Time: 2:16 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="edu.iuh.fit.backEnd.services.ProductService" %>
<%@ page import="edu.iuh.fit.backEnd.models.Product" %>
<%@ page import="edu.iuh.fit.backEnd.models.Productprice" %>
<%@ page import="edu.iuh.fit.backEnd.models.Productimage" %>
<%@ page import="java.util.List" %>
<%@ page import="edu.iuh.fit.backEnd.models.Productprice" %>
<%@ page import="java.util.Formatter" %>
<html>
<head>
    <title>Product</title>
    <style>
        #container {
            width: 100vh;
            margin: 1% 10% 0 10%;
            justify-content: center;
            align-items: center;
            flex-direction: column;
        }
    </style>
    <script>
        function addNewPrice() {
            const priceInput = document.getElementById('priceInput');
            const txtPriceNote = document.getElementById('txtPriceNote');
            priceInput.style.display = 'none';
            priceInput.value = '';
            const txtNewPrice = document.getElementById('txtNewPrice');
            txtNewPrice.style.display = 'block';
            txtPriceNote.style.display = 'block';

            const editButton = document.getElementById('buttonNewPrice');
            editButton.style.content = 'Cancel';
            editButton.setAttribute('onclick', 'cancelNewPrice()');
        }

        function cancelNewPrice() {
            const priceInput = document.getElementById('priceInput');
            priceInput.style.display = 'block';

            const txtNewPrice = document.getElementById('txtNewPrice');
            txtNewPrice.style.display = 'none';
            txtNewPrice.value = '';
            const txtPriceNote = document.getElementById('txtPriceNote');
            txtPriceNote.style.display = 'none';
            txtPriceNote.value = '';

            const editButton = document.getElementById('buttonNewPrice');
            editButton.style.content = 'New';
            editButton.setAttribute('onclick', 'addNewPrice()');

        }

        function previewImage() {
            const fileInput = document.getElementById('fileInput');
            const urlInput = document.getElementById('urlInput');
            const preview = document.getElementById('imagePreview');

            if (fileInput.files && fileInput.files[0]) {
                const reader = new FileReader();
                reader.onload = function (e) {
                    preview.src = e.target.result;
                };
                reader.readAsDataURL(fileInput.files[0]);
            } else if (urlInput.value) {
                preview.src = urlInput.value;
            } else {
                preview.src = '';
            }
        }
    </script>
</head>
<body>
<%
    ProductService ser = new ProductService();
    List<Productprice> productprices = ser.getAllPrice();

%>
<div id="container">
    <h3>Insert new product</h3>
    <form action="controls?action=insert_products" method="post">

        <table>
            <tr>
                <td>Product Name:</td>
                <td><input type="text" name="name"></td>
            </tr>
            <tr>
                <td>Description:</td>
                <td><input type="text" name="description"></td>
            </tr>
            <tr>
                <td>Unit:</td>
                <td><input type="text" name="unit"></td>
            </tr>
            <tr>
                <td>Manufacturer:</td>
                <td><input type="text" name="manufacturer"></td>
            </tr>
            <tr>
                <td>Status:</td>
                <td>
                    <select name="status">
                        <option label="ACTIVE">ACTIVE</option>
                        <option label="IN_ACTIVE">IN_ACTIVE</option>
                        <option label="TERMINATED">TERMINATED</option>
                    </select>
                </td>

            </tr>
            <tr>
                <td>Price</td>
                <td>
                    <select name="price" id="priceInput">
                        <%
                            Formatter formatter = new Formatter();
                            for (Productprice productprice : productprices) {
                        %>
                        <option value="<%=productprice.getId().getPriceDateTime()%>;<%=productprice.getPrice()%>">
                            <%=productprice.getPrice()%>
                        </option>
                        <%
                            }
                        %>
                    </select>
                    <input type="text" name="priceNew" placeholder="new price" id="txtNewPrice" style="display: none">
                    <input type="text" name="noteNew" placeholder="Note" id="txtPriceNote" style="display: none">
                </td>
                <td>
                    <button id="buttonNewPrice" type="button" onclick="addNewPrice()">New</button>
                </td>
            </tr>

            <tr>
                <td>Image File:</td>
                <td><input type="file" id="fileInput" name="imageFile" accept="image/*" onchange="previewImage()"></td>
            </tr>
            <tr>
                <td>Image URL:</td>
                <td><input type="text" id="urlInput" name="imageUrl" placeholder="Enter image URL"
                           oninput="previewImage()"></td>
            </tr>
            <tr>
                <td colspan="2">
                    <img id="imagePreview" src="" alt="Image Preview" style="max-width: 100px; max-height: 100px;">
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
