package com.atguigu.web;

import com.atguigu.pojo.Book;
import com.atguigu.pojo.Page;
import com.atguigu.service.BookService;
import com.atguigu.service.impl.BookServiceImpl;
import com.atguigu.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ClientBookServlet extends BaseServlet{

    private BookService bookService = new BookServiceImpl();

    protected void page(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1 获取请求的参数 pageNo 和 pageSize
        int pageNo = WebUtils.parseInt(req.getParameter("pageNo"), 1);
        int pageSize = WebUtils.parseInt(req.getParameter("pageSize"), Page.PAGE_SIZE);

        //2 调用BookService.page(pageNo，pageSize)：Page对象
        Page<Book> page = bookService.page(pageNo, pageSize);

        //抽取前后端的分页条，因为分页条中请求的Servlet不同，因此，我们只能在这里设置路径地址了
        page.setUrl("client/bookServlet?action=page");

        //3 保存Page对象到Request域中
        req.setAttribute("page", page);

        //4 请求转发到pages/manager/book_manager.jsp页面
        req.getRequestDispatcher("/pages/client/index.jsp").forward(req, resp);
    }

    //价格区间查询，我们只需要返回Page分页数据，index.jsp页面，会自动识别，并展示
    protected void pageByPrice(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1 获取请求的参数 pageNo 和 pageSize
        int pageNo = WebUtils.parseInt(req.getParameter("pageNo"), 1);
        int pageSize = WebUtils.parseInt(req.getParameter("pageSize"), Page.PAGE_SIZE);

        int min = WebUtils.parseInt(req.getParameter("min"), 0);
        int max = WebUtils.parseInt(req.getParameter("max"), Integer.MAX_VALUE);

        //2 调用BookService.pageByPrice(pageNo, pageSize, min, max);Page对象
        Page<Book> page = bookService.pageByPrice(pageNo, pageSize, min, max);

        //因为虽然第一步按照价格查询了，接下来仍然要在地址中传入价格区间，要不然就是按照0-Integer.MAX_VALUE的区间查询，这样不行
        StringBuilder sb = new StringBuilder("client/bookServlet?action=pageByPrice");
        //如果有最小价格参数，最佳到分页条的地址参数中
        if (req.getParameter("min") != null){
            sb.append("&min=").append(req.getParameter("min"));
        }
        //如果有最大价格参数，最佳到分页条的地址参数中
        if (req.getParameter("max") != null){
            sb.append("&max=").append(req.getParameter("max"));
        }

        //抽取前后端的分页条，因为分页条中请求的Servlet不同，因此，我们只能在这里设置路径地址了
        page.setUrl(sb.toString());

        //3 保存Page对象到Request域中
        req.setAttribute("page", page);

        //4 请求转发到pages/manager/book_manager.jsp页面
        req.getRequestDispatcher("/pages/client/index.jsp").forward(req, resp);
    }

}
