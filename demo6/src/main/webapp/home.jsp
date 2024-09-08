<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%!
    int cong(int a, int b) {
        return a + b;
    }
    List <String> lst =new ArrayList<>();
%>
<h1>SAMPLE LINE</h1>
<h2> 3 + 5 = <%=cong(3, 5)%>
</h2>
<hr/>

<%@include file="index.html"%>

<hr/>
<jsp:include page="index.html"/>
<%--
<%
    for (int i = 2; i <= 10; i++) {
        for (int j = 1; j <= 10; j++) {
            %>
            <p><%=i%> * <%=j%> = <%=i*j%></p>
<%
        }
        %>
        <hr width="50%"/>
<%
    }
%>--%>
