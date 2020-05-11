<%--
  Created by IntelliJ IDEA.
  User: 18137
  Date: 20.5.7
  Time: 11:34:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <form action="http://localhost:8080/091_EL_JSTL/uploadServlet" method="post" enctype="multipart/form-data">
        用户名：<input type="text" name="username" /> <br>
        头像: <input type="file" name="photo" /> <br>
        <input type="submit" value="上传">
    </form>
</body>
</html>
