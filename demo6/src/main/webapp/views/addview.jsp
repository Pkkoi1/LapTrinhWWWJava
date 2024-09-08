
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<h1>
    <%
        int result = Integer.parseInt(session.getAttribute("result").toString());
    %>
    Result = <%=result%>
</h1>
