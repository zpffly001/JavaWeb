<%--
  Created by IntelliJ IDEA.
  User: 18137
  Date: 20.5.6
  Time: 19:28:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <%
        //往四个域中都保存了相同的key的数据。
        pageContext.setAttribute("key","pageContext");
        request.setAttribute("key", "request");
        session.setAttribute("key", "session");
        application.setAttribute("key", "servletContext");
    %>
    ${key}
</body>
</html>
