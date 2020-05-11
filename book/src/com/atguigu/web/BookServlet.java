package com.atguigu.web;

import com.atguigu.pojo.Book;
import com.atguigu.pojo.Page;
import com.atguigu.service.BookService;
import com.atguigu.service.impl.BookServiceImpl;
import com.atguigu.utils.WebUtils;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class BookServlet extends BaseServlet{

    private BookService bookService = new BookServiceImpl();

    protected void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //当前页数，我们这里加1，就可以找到正确的页数了
        int pageNo = WebUtils.parseInt(req.getParameter("pageNo"), 0);
        pageNo += 1;
        //        1、获取请求的参数==封装成为Book对象
        Book book = WebUtils.copyParmToBean(req.getParameterMap(), new Book());
        //        2、调用BookService.addBook()保存图书
        bookService.addBook(book);
        //        3、跳到图书列表页面,如果使用请求转发，浏览器会记录最后一次请求的内容，而最后一次请求正是添加操作，因此会出现重复添加，即表单重复提交
        //                /manager/bookServlet?action=list
                //req.getRequestDispatcher("/manager/bookServlet?action=list").forward(req, resp);
        //而且，请求转发也会把Servlet写在网址上，这样不安全，，因而为了防止表单重复提交，和安全，我们使用请求重定向
        //请求转发的/斜杠表示到工程名，而请求重定向的/只表示到port端口号，因此我们要显示的加上工程名/book
        resp.sendRedirect(req.getContextPath() + "/manager/bookServlet?action=page&pageNo=" + pageNo);
        //重定向原理，重定向可以看作客户端向服务器发送了两次请求，第二次请求时，地址栏会发生变化，因此，不会进行添加操作
        //地址栏变成了http://localhost:8080/book/manager/bookServlet?action=list，而后面没有了书名，作者等参数，因此，在此刷新页面，会执行这个list()方法查询，而不会进行添加操作

    }

    protected void page(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1 获取请求的参数 pageNo 和 pageSize
        int pageNo = WebUtils.parseInt(req.getParameter("pageNo"), 1);
        int pageSize = WebUtils.parseInt(req.getParameter("pageSize"), Page.PAGE_SIZE);

        //2 调用BookService.page(pageNo，pageSize)：Page对象
        Page<Book> page = bookService.page(pageNo, pageSize);

        //抽取前后端的分页条，因为分页条中请求的Servlet不同，因此，我们只能在这里设置路径地址了
        page.setUrl("manager/bookServlet?action=page");

        //3 保存Page对象到Request域中
        req.setAttribute("page", page);

        //4 请求转发到pages/manager/book_manager.jsp页面
        req.getRequestDispatcher("/pages/manager/book_manager.jsp").forward(req, resp);
    }

    protected void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //        1、获取请求的参数==封装成为Book对象
        Book book = WebUtils.copyParmToBean(req.getParameterMap(), new Book());
        //        2、调用BookService.updateBook( book );修改图书
        bookService.updateBook(book);
        //        3、重定向回图书列表管理页面
        //        地址：/工程名/manager/bookServlet?action=list
        resp.sendRedirect(req.getContextPath() + "/manager/bookServlet?action=page&pageNo=" + req.getParameter("pageNo"));
    }

    protected void getBook(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求参数，即要回显的图书的id
        int id = WebUtils.parseInt(req.getParameter("id"), 0);
        //调用bookService查询图书
        Book book = bookService.queryBookById(id);
        //把查询到的数据存到request域中
        req.setAttribute("book", book);
        //请求转发到对应的jsp页面，页面提取数据进行回显
        req.getRequestDispatcher("/pages/manager/book_edit.jsp").forward(req, resp);
    }

    protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //        1、获取请求的参数id，图书编程
        int id = WebUtils.parseInt(req.getParameter("id"), 0);
        //        2、调用bookService.deleteBookById();删除图书
        bookService.deleteBookById(id);
        //        3、重定向回图书列表管理页面
        //            /book/manager/bookServlet?action=list
        resp.sendRedirect(req.getContextPath() + "/manager/bookServlet?action=page&pageNo=" + req.getParameter("pageNo"));
    }

    protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1 通过BookService查询全部图书
        List<Book> books = bookService.queryBooks();
        //2 把全部图书保存到Request域中
        req.setAttribute("books", books);
        //3、请求转发到/pages/manager/book_manager.jsp页面
        req.getRequestDispatcher("/pages/manager/book_manager.jsp").forward(req, resp);
    }

}
