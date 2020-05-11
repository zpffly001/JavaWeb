package com.atguigu.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;

/**
 * 我们把方法调用这个通用的方法提取出来，让其他Servlet继承这个类就行了，达到了代码的复用
 * 其他的Servlet不用再继承HttpServlet，继承这个BaseServlet就行了
 * 虽然调用的是子Servlet模块，但是子类会调用父类构造器实例化父类结构，然后从上往下执行，
 * 又因为只要继承Servlet或只要是Servlet程序都必须执行service方法，即doPost方法也会执行
 * 因此通过多态可知，父类是抽象类不能实例化，虽然是父类接口类型，但是实际上new的是子类对象，
 * 因此doPost方法的实际调用者就是每个子Servlet，因为this.getClass()就是子类类对象
 *
 * 步骤就是
 * 1，form表单的action属性执行某个子Servlete
 * 2，下面定义一个隐藏域，然后定义name="action", value=字符串，
 * 3，doPost方法中，通过request.getParmer("action) 得出Value的值，
 * 4，以Value的值作为方法名，在子Servlet中定义方法
 * 5，BaseServlet会被子Servlet初始化结构，然后子Servlet相当于调用了doPost方法，并通过反射调用了子类中定义的方法，执行了相应的操作
 */
public abstract class BaseServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //解决请求中文乱码的问题，一定要在获取请求参数之前调用才有效
        req.setCharacterEncoding("UTF-8");
        //解决响应的中文乱码
        resp.setContentType("text/html; Charset=UTF-8");

        String action = req.getParameter("action");


        try {
            //通过反射，在UserServletTest类或者父类(因为加了Declared)得到对应名称的方法对象,如果加上method.setAccessible(true);就表明什么保险及别的都能调用包括private修饰的
            Method method = this.getClass().getDeclaredMethod(action, HttpServletRequest.class, HttpServletResponse.class);

            //方法调用，要指明是该类的哪个对象的调用，因为一个类可以实例化多个对象
            method.invoke(this, req, resp);//参数哪个属性可有可无，我们这里不需要参数，因此不写参数这个形参

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

}
