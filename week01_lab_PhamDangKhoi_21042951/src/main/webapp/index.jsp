<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<h3><%= "Ten tai khoan" %>
</h3>
<input type="hidden" name="action" value="add"/>

<h3><%= "Mat khau" %>
</h3>
<input type="hidden" name="action" value="add"/>
<br/>
<a href="hello-servlet">Hello Servlet</a>
</body>
</html>