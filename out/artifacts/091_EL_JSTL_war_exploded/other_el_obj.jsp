<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 18137
  Date: 20.5.6
  Time: 21:54:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%--el表达式中param对象，表示请求参数--%>
输出请求参数username的值：${param.username} <br>
输出请求参数password的值：${ param.password } <br>

输出请求参数String[]  username的值：${paramValues.username[0]}
输出请求参数hobby的值：${paramValues.hobby[0]} <br>
输出请求参数hobby的值：${paramValues.hobby[1]} <br>
<hr>
输出请求头【User-Agent】的值：${ header['User-Agent'] } <br>
输出请求头【Connection】的值：${ header.Connection } <br>
输出请求头，多个参数，数组形式存储【User-Agent】的值：${ headerValues['User-Agent'][0] } <br>
<hr>
获取Cookie的名称：${cookie.JSESSIONID.name} <br>
获取Cookie的值：${cookie.JSESSIONID.value} <br>
<hr>
输出&lt;Context-param&gt;username的值：${ initParam.username } <br>
输出&lt;Context-param&gt;url的值：${ initParam.password } <br>



</body>
</html>
