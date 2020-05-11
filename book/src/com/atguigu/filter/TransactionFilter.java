package com.atguigu.filter;

import com.atguigu.utils.jdbcUtils;

import javax.servlet.*;
import java.io.IOException;

public class TransactionFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        try {
            filterChain.doFilter(servletRequest, servletResponse);

            jdbcUtils.commitAndClose();//提交事务

        } catch (Exception e) {
            jdbcUtils.rollbackAndClose();//回滚事务
            e.printStackTrace();
            throw new RuntimeException(e);//你捕获你的异常，做你的事务回滚，但是不要忘了，也把异常抛给Tomcat服务器，让服务器进行，错误页面跳转
        }

    }

    @Override
    public void destroy() {

    }
}
