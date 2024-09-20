<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="edu.iuh.fit.backEnd.services.ProductService" %>
<%@ page import="edu.iuh.fit.backEnd.models.Product" %>
<%@ page import="edu.iuh.fit.backEnd.models.Productimage" %>
<%@ page import="java.util.List" %>
<%@ page import="edu.iuh.fit.backEnd.models.Productprice" %>
<html>
<head>
    <title>Product</title>

    <style>
        #container {
            width: 70%;
            margin: auto;
        }
        .product_item {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            grid-gap: 10px;
            border: 1px solid #ccc;
            padding: 10px;
        }
        img {
            width: 20vh;
            height: 20vh;
        }
        #product_detail {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            grid-gap: 10px;

        }
        #product_actionButtons {
            display: grid;
            grid-template-columns: repeat(2, 1fr);
            grid-gap: 10px;
        }
    </style>
</head>
<body>

<div id="container">
    <h3 align="center">ProductList</h3>
    <button style="margin-bottom: 2vh"><a href="insertProduct.jsp">Insert</a></button>
    <div id="product_List">
        <%
            ProductService productService = new ProductService();
            List<Product> productList = productService.getAll();
        %>
        <% for(Product product: productList) {
            long id = product.getId();
            String delete_string = "controls?action=delete_product&id=" + id;
            if(product.getStatus().getValue() != 1) {
                delete_string = "controls?action=active_product&id=" + id;
            }
            String edit_string = "controls?action=edit_product&id=" + id;
            Productimage randomImage = productService.getRandomProductImage(id);
        %>
        <div class="product_item">
            <div>
                <% if (randomImage != null) { %>
                <img src="<%= randomImage.getPath() %>" srcset="<%= randomImage.getAlternative() %>">
                <% } %>
            </div>
            <div id="product_detail">
                <p><strong>Name:</strong> <%= product.getName() %></p>
                <p><strong>Description:</strong> <%= product.getDescription() %></p>
                <p><strong>Unit:</strong> <%= product.getUnit() %></p>
                <p><strong>Manufacturer:</strong> <%= product.getManufacturerName() %></p>
                <p><strong>Status:</strong>
                    <span style="<%= product.getStatus().getValue() == 1 ? "color: green;" : product.getStatus().getValue() == 0 ? "color: orange;" : "color: red;" %>">
                                 <%= product.getStatus().getValue() == 1 ? "\nĐang kinh doanh" : product.getStatus().getValue() == 0 ? "\nTạm ngưng" : "\nKhông kinh doanh nữa" %>
                     </span>
                </p>
                <p><strong>Price:</strong>
                    <% for(Productprice price : productService.getProductByPrice(id)) { %>
                    <%= price.getPrice() %>
                    <% } %>
                </p>
            </div>
            <div id="product_actionButtons">
                <div>
                    <a href="<%= edit_string %>">Update</a>
                </div>
                <div>
                    <button onclick= "window.location.href='<%=delete_string%>';"><a href=<%=delete_string%>><%= product.getStatus().getValue() == 1 ? "Delete": "Active"%></a></button>

                </div>
                <div>
                    <button><a href="index.jsp">Back</a></button>
                </div>
                <div>
                    <button>Add to chart</button>
                </div>
            </div>
        </div>
        <% } %>
    </div>
</div>

</body>
</html>