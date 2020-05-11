package com.atguigu.web;

import com.atguigu.pojo.User;
import com.atguigu.service.UserService;
import com.atguigu.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 为什么要先请求Servlet程序再
 */
public class LoginServlet extends HttpServlet {

    private UserService userService = new UserServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //  1、获取请求的参数
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        // 调用 userService.login()登录处理业务
        User loginUser = userService.login(new User(null, username, password, null));
        // 如果等于null,说明登录 失败!
        if (loginUser == null){
            //   跳回登录页面
            // 把错误信息，和回显的表单项信息，保存到Request域中，进行数据的回显，因为是请求转发，只有一个request,所以这也数据转发到页面也不会消失
            req.setAttribute("msg","用户或密码错误！");
            req.setAttribute("username" , username);
            req.getRequestDispatcher("/pages/user/login.jsp").forward(req, resp);
        }else {
            // 登录 成功
            //跳到成功页面login_success.html
            System.out.println(loginUser);
            req.getRequestDispatcher("/pages/user/login_success.jsp").forward(req, resp);
        }
    }
}
