
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <%
        String x = application.getRealPath(".");
        out.println(x);
        x = request.getParameter("a");
        out.println(x);
    %>
</body>
</html>
