<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.atguigu.pojo.Student" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 18137
  Date: 20.5.7
  Time: 9:07:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%--1.遍历1到10，输出
        begin属性设置开始的索引
        end 属性设置结束的索引
        var 属性表示循环的变量(也是当前正在遍历到的数据)--%>
<c:forEach begin="1" end="10" var="i">
    ${i}
</c:forEach>
<hr>

<%-- 2.遍历Object数组
     for (Object item: arr)
     items 表示遍历的数据源（遍历的集合）
     var 表示当前遍历到的数据--%>
<%
    request.setAttribute("arr", new String[]{"110", "120", "119"});
%>
<c:forEach items="${requestScope.arr}" var="item">
    ${item}
</c:forEach>
<hr>

<%
    Map<Object, String> map = new HashMap<>();
    map.put("key1", "value1");
    map.put("key2", "value2");
    map.put("key3", "value3");
//        for ( Map.Entry<String,Object> entry : map.entrySet()) {
//        }
    request.setAttribute("map", map);
%>
<c:forEach items="${requestScope.map}" var="item">
    ${item}
    ${item.key}
    ${item.value}
</c:forEach>
<hr>

<%--4.遍历List集合---list中存放 Student类，有属性：编号，用户名，密码，年龄，电话信息--%>
<%
    List<Student> studentList = new ArrayList<Student>();
    for (int i = 1; i <= 10; i++) {
        studentList.add(new Student(i,"username"+i ,"pass"+i,18+i,"phone"+i));
    }
    request.setAttribute("stus", studentList);
%>
<c:forEach items="${requestScope.stus}" var="item">
    ${item}
    ${item.username}
</c:forEach>
<hr>

<%--
            items 表示遍历的集合
            var 表示遍历到的数据
            begin表示遍历的开始索引值
            end 表示结束的索引值
            step 属性表示遍历的步长值
            varStatus 属性表示当前遍历到的数据的状态
            for（int i = 1; i < 10; i+=2）
        --%>
<c:forEach begin="2" end="7" step="2" varStatus="status" items="${requestScope.stus}" var="stu">
    <tr>
        <td>${stu.id}</td>
        <td>${stu.username}</td>
        <td>${stu.password}</td>
        <td>${stu.age}</td>
        <td>${stu.phone}</td>
        <td>${status.step}</td>
    </tr>
</c:forEach>
</body>
</html>
