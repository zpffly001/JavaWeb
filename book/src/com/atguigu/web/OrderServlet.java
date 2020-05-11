package com.atguigu.web;

import com.alibaba.druid.util.JdbcUtils;
import com.atguigu.pojo.Cart;
import com.atguigu.pojo.User;
import com.atguigu.service.OrderService;
import com.atguigu.service.impl.OrderServiceImpl;
import com.atguigu.utils.jdbcUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OrderServlet extends BaseServlet{

    private OrderService orderService = new OrderServiceImpl();

    /**
     * 生成订单
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void createOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //我们调用生成订单的Service时，需要传入Cart购物车对象，和UserID
        //先获取Cart购物车对象
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        //再获取UserId，因为我们在登陆时，就把User对象存放在了Session域中，因此可以从Session域中获取UserID
        User user = (User) req.getSession().getAttribute("user");
        if (user == null){
            req.getRequestDispatcher("/pages/user/login.jsp").forward(req, resp);
            return;
        }
        Integer id = user.getId();
        //生成订单,因为这个方法需要，orderDao.saveOrder(order);保存订单方法和orderItemDao.saveOrderItem(orderItem);保存订单项方法和bookDao.updateBook(book);更新图书销量库存三个方法共同完成
        //这三个方法都需要获取连接，使用连接，因此我们也要在这里进行异常的捕获
        String orderId = orderId = orderService.createOrder(cart, id);
        //jdbcUtils.commitAndClose();//如果Dao层方法没有异常抛出，就进行事务的提交，和连接的关闭
        //jdbcUtils.rollbackAndClose();//如果Dao层方法有异常抛出，就进行事务的回滚，和连接的关闭
        //又因为我们在Filter配置了拦截所有请求/* 来捕获异常，因此就不用在每一个Servlet程序中进行try-cach了


        //request域存储+请求转发会出现表单重复提交的问题
        //订单号保存到request域中，我们可以输出到jsp页面
        //req.setAttribute("orderId", orderId);
        // 请求转发到/pages/cart/checkout.jsp
        //req.getRequestDispatcher("/pages/cart/checkout.jsp").forward(req, resp);


        //为了防止表单重复提交，我们使用Session存储对象+重定向跳转
        req.getSession().setAttribute("orderId",orderId);
        resp.sendRedirect(req.getContextPath()+"/pages/cart/checkout.jsp");
    }
}
