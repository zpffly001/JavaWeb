<%--
  Created by IntelliJ IDEA.
  User: 18137
  Date: 20.5.6
  Time: 19:22:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <%
        request.setAttribute("key", "值");
    %>
    jsp输出 <%= request.getAttribute("key") %>
    el表达式输出 ${key}
</body>
</html>
