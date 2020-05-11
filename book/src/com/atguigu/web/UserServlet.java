package com.atguigu.web;

import com.atguigu.pojo.User;
import com.atguigu.service.UserService;
import com.atguigu.service.impl.UserServiceImpl;
import com.atguigu.test.UserServletTest;
import com.atguigu.utils.WebUtils;
import com.google.gson.Gson;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY;

public class UserServlet extends BaseServlet {

    private UserService userService = new UserServiceImpl();

    protected void  ajaxExistsUsername(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求的参数username
        String username = req.getParameter("username");
        //调用Service验证用户名是否存在
        boolean existsUsername = userService.existsUsername(username);
        //把结果封装成Map对象，回传给客户端,因为使用的是返回JSON对象的Agax异步请求，因此我们需要把结果放入Map，并转化为JSON对象，返回
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("existsUsername", existsUsername);

        Gson gson = new Gson();
        String json = gson.toJson(resultMap);//Map转换为Json字符串，在网络中传输
        resp.getWriter().write(json);
    }

    protected void  logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1,销毁Session中的用户登录信息，或者销毁Session
        req.getSession().invalidate();//因为一个浏览和和该服务器只有一个Session对象，因此可以直接设置Session立即超时，即销毁Session
        //2，重定向到首页
        resp.sendRedirect(req.getContextPath());//访问到工程路径，默认就是访问index.jsp
    }

    protected void  login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
            //很多页面都需要用到当前登录名：韩总，放在Request域中已经不够了，因此从小到大，我们把用户登录信息放在上一层即Session域中
            req.getSession().setAttribute("user", loginUser);
            // 登录 成功
            //跳到成功页面login_success.html
            System.out.println(loginUser);
            req.getRequestDispatcher("/pages/user/login_success.jsp").forward(req, resp);
        }
    }

    protected void  regist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 获取 Session 中的验证码
        String token = (String) req.getSession().getAttribute(KAPTCHA_SESSION_KEY);
        // 删除 Session 中的验证码
        req.getSession().removeAttribute(KAPTCHA_SESSION_KEY);

        //  1、获取请求的参数
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String code = req.getParameter("code");

//        User user = new User();
//        WebUtils.copyParmToBean(req.getParameterMap(), user);

        //这行代码代替了上面的两行代码，只不过需要让WebUtils的注入方法给我们返回一个Object类型的对象
        User user = WebUtils.copyParmToBean(req.getParameterMap(), new User());

        //        2、检查 验证码是否正确  === 写死,要求验证码为:abcde
        if (token != null && token.equalsIgnoreCase(code)){
            //        3、检查 用户名是否可用
            if (userService.existsUsername(username)){
                //用户名已存在，不可用
                System.out.println("用户名[" + username + "]已存在");

                // 把回显信息，保存到Request域中
                req.setAttribute("msg", "用户名已存在！！");
                req.setAttribute("username", username);
                req.setAttribute("email", email);
                req.getRequestDispatcher("/pages/user/regist.jsp").forward(req, resp);
            }else {
                //用户名可用
                userService.registUser(new User(null,username,password,email));
                //转发到注册成功页面
                req.getRequestDispatcher("/pages/user/regist_success.jsp").forward(req, resp);
            }
        }else {

            // 把回显信息，保存到Request域中
            req.setAttribute("msg", "验证码错误！！");
            req.setAttribute("username", username);
            req.setAttribute("email", email);

            //不正确跳回注册页面,请求转发的方式
            System.out.println("验证码[" + code + "]错误");
            req.getRequestDispatcher("/pages/user/regist.jsp").forward(req, resp);
        }
    }

}
