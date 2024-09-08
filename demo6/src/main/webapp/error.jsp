<%--
  Created by IntelliJ IDEA.
  User: VoVanHai
  Date: 07/09/2024
  Time: 14:39 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"
         language="java"
         isErrorPage="true"
%>
<html>
<head>
    <title>Error Page</title>
</head>
<body>
    <h1>
        ERROR PAGE
    </h1>
<h2>Your error is :<%=exception.getMessage()%></h2>
<h2>The cause  is :<%=exception.getCause()%></h2>
</body>
</html>
