<%--
  Created by IntelliJ IDEA.
  User: DANG KHOI
  Date: 9/17/2024
  Time: 2:16 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Product</title>
    <style>
        #container
        {
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
                   <td><input type="submit" value="Insert"></td>
                   <td><input type="reset" value="Reset"></td>
               </tr>
           </table>
       </form>
   </div>
</body>
</html>
