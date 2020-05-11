<%--
  Created by IntelliJ IDEA.
  User: 18137
  Date: 20.5.7
  Time: 17:03:43
  To change this template use File | Settings | File Templates.
  <%--    http + :// + 192.168.1.13 + : + 8080 + /book + /
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%

    String basePath = request.getScheme()
            + "://"
            + request.getServerName()
            + ":"
            + request.getServerPort()
            + request.getContextPath()
            + "/";
    request.setAttribute("basePath", basePath);
%>

<div>
    <span>欢迎<span class="um_span">${sessionScope.user.username}</span>光临尚硅谷书城</span>
    <a href=pages/order/order.jsp>我的订单</a>
    <a href="userServlet?action=logout">注销</a>&nbsp;&nbsp;
    <a href="index.jsp">返回</a>
</div>
