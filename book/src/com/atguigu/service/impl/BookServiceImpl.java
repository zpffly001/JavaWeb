package com.atguigu.service.impl;

import com.atguigu.dao.BookDao;
import com.atguigu.dao.impl.BookDaoImpl;
import com.atguigu.pojo.Book;
import com.atguigu.pojo.Page;
import com.atguigu.service.BookService;

import java.util.List;

public class BookServiceImpl implements BookService {

    BookDao bookDao = new BookDaoImpl();

    @Override
    public void addBook(Book book) {
        bookDao.addBook(book);
    }

    @Override
    public void deleteBookById(Integer id) {
        bookDao.deleteBookById(id);
    }

    @Override
    public void updateBook(Book book) {
        bookDao.updateBook(book);
    }

    @Override
    public Book queryBookById(Integer id) {
        return bookDao.queryBookById(id);
    }

    @Override
    public List<Book> queryBooks() {
        return bookDao.queryBooks();
    }

    @Override
    public Page<Book> page(int pageNo, int pageSize) {

        Page<Book> page = new Page<>();


        // 设置每页显示的数量
        page.setPageSize(pageSize);

        //求出总记录数
        Integer pageTotalCount = bookDao.queryForPageTotalCount();
        //设置总记录个数
        page.setPageTotalCount(pageTotalCount);

        //求总页码数
        Integer pageTotal = pageTotalCount / pageSize;
        if (pageTotalCount % pageSize > 0){
            pageTotal++;
        }
        //设置总页码数
        page.setPageTotal(pageTotal);


        // 设置当前页码
        page.setPageNo(pageNo);

        //求当前页的开始页码数
        int begin = (page.getPageNo() - 1) * pageSize;
        //求当前页码的几条图书数据的List集合
        List<Book> items = bookDao.queryForPageItem(begin, pageSize);
        //设置当前页码的几条图书数据到Page对象中
        page.setItems(items);

        return page;
    }

    //根据价格区间，返回分页对象
    @Override
    public Page<Book> pageByPrice(int pageNo, int pageSize, int min, int max) {

        Page<Book> page = new Page<>();


        // 设置每页显示的数量
        page.setPageSize(pageSize);

        //求出总记录数
        Integer pageTotalCount = bookDao.queryForPageTotalCountByPrice(min, max);
        //设置总记录个数
        page.setPageTotalCount(pageTotalCount);

        //求总页码数
        Integer pageTotal = pageTotalCount / pageSize;
        if (pageTotalCount % pageSize > 0){
            pageTotal++;
        }
        //设置总页码数
        page.setPageTotal(pageTotal);


        // 设置当前页码
        page.setPageNo(pageNo);

        //求当前页的开始页码数
        int begin = (page.getPageNo() - 1) * pageSize;
        //求当前页码的几条图书数据的List集合
        List<Book> items = bookDao.queryForPageItemByPrice(begin, pageSize, min, max);
        //设置当前页码的几条图书数据到Page对象中
        page.setItems(items);

        return page;
    }
}
