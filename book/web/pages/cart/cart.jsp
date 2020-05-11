<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>购物车</title>
	<%@include file="/pages/common/head.jsp"%>

	<script type="text/javascript">
		$(function () {
			//提示是否要删除
			$("a.deleteItem").click(function () {
				return confirm("你确定要删除【"+ $(this).parent().parent().find("td:first").text() +"】吗");
			});

			//提示是否清空购物车,因为清空购物车只有一个(标签中只有href一个参数)，因此我们使用id，就能唯一的标识这个操作
			$("#clearCart").click(function () {
				return confirm("你确定要清空购物车吗");
			});

			//给输入框绑定失去焦点事件 blur()事件只要失去焦点就会触发，而change()事件则是，输入框内容发生变化才会触发
			$(".updateCount").change(function () {

				//获取商品名称，此时$(this)代表当前的数量输入框
				var name = $(this).parent().parent().find("td:first").text();
				//获取商品的ID
				var id = $(this).attr('bookId');
				//获取商品的数量，此时$(this)代表当前的数量输入框，因此我们获取输入框的value属性就获得了商品的数量
				var count = this.value;
				if (confirm("你确定要将【"+ name +"】商品的数量修改为【"+ count +"】吗？")){
					//发起请求，给服务器保存修改
					location.href = "http://localhost:8080/book/cartServlet?action=updateCount&count="+count+"&id="+id;
				}else {
					//defaultValue属性是表单项Dom对象的属性，它表示默认的value值，即之前展示的值
					this.value = this.defaultValue;
				}

			// // 获取商品名称
			// var name = $(this).parent().parent().find("td:first").text();
			// var id = $(this).attr('bookId');
			// // 获取商品数量
			// var count = this.value;
			// if ( confirm("你确定要将【" + name + "】商品修改数量为：" + count + " 吗?") ) {
			// 	//发起请求。给服务器保存修改
			// 	location.href = "http://localhost:8080/book/cartServlet?action=updateCount&count="+count+"&id="+id;
			// } else {
			// 	// defaultValue属性是表单项Dom对象的属性。它表示默认的value属性值。
			// 	this.value = this.defaultValue;
			// }

			});
		});
	</script>
</head>
<body>
	
	<div id="header">
			<img class="logo_img" alt="" src="static/img/logo.gif" >
			<span class="wel_word">购物车</span>
		<%--公共模块抽取掉，然后使用静态包含引入--%>
		<%@include file="/pages/common/login_success_menu.jsp"%>
	</div>
	
	<div id="main">
	
		<table>
			<tr>
				<td>商品名称</td>
				<td>数量</td>
				<td>单价</td>
				<td>金额</td>
				<td>操作</td>
			</tr>

			<c:if test="${empty sessionScope.cart.items}">
				<%--购物车为空的情况下--%>
				<tr>
					<td colspan="5"><a href="index.jsp">亲，当前购物车为空,快跟小伙伴们去浏览商品吧</a></td>
				</tr>
			</c:if>

			<c:if test="${not empty sessionScope.cart.items}">
			<%--购物车不为空的情况下--%>
			<c:forEach items="${sessionScope.cart.items}" var="item">
				<tr>
					<td>${item.value.name}</td>
					<td>
						<input class="updateCount" bookId="${item.value.id}" style="width: 80px;" type="text" value="${item.value.count}">
					</td>
					<td>${item.value.price}</td>
					<td>${item.value.totalPrice}</td>
					<td><a class="deleteItem" href="cartServlet?action=deleteItem&id=${item.value.id}">删除</a></td>
				</tr>
			</c:forEach>
			</c:if>
			
		</table>

		<c:if test="${not empty sessionScope.cart.items}">
		<div class="cart_info">
			<span class="cart_span">购物车中共有<span class="b_count">${sessionScope.cart.totalCount}</span>件商品</span>
			<span class="cart_span">总金额<span class="b_price">${sessionScope.cart.totalPrice}</span>元</span>
			<span class="cart_span"><a id="clearCart" href="cartServlet?action=clear">清空购物车</a></span>
			<span class="cart_span"><a href="orderServlet?action=createOrder">去结账</a></span>
		</div>
		</c:if>

	</div>

	<%@include file="/pages/common/footer.jsp"%>
</body>
</html>