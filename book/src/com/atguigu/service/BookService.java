package com.atguigu.service;

import com.atguigu.pojo.Book;
import com.atguigu.pojo.Page;

import java.util.List;

/**
 * Service调用Dao层方法时，对于增删改这些操作我们就不需要返回值了，但是在Dao层增删改，这些操作还是要返回一个数值的，比如说-1为操作失败，然后我们Service层接收到反馈，输出页面是否成功
 */
public interface BookService {

    void addBook(Book book);

    void deleteBookById(Integer id);

    void updateBook(Book book);

    Book queryBookById(Integer id);

    List<Book> queryBooks();

    Page<Book> page(int pageNo, int pageSize);

    Page<Book> pageByPrice(int pageNo, int pageSize, int min, int max);
}
