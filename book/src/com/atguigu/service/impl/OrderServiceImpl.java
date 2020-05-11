package com.atguigu.service.impl;

import com.atguigu.dao.BookDao;
import com.atguigu.dao.OrderDao;
import com.atguigu.dao.OrderItemDao;
import com.atguigu.dao.impl.BookDaoImpl;
import com.atguigu.dao.impl.OrderDaoImpl;
import com.atguigu.dao.impl.OrderItemDaoImpl;
import com.atguigu.pojo.*;
import com.atguigu.service.OrderService;

import java.util.Date;
import java.util.Map;

public class OrderServiceImpl implements OrderService {

    private OrderDao orderDao = new OrderDaoImpl();
    private OrderItemDao orderItemDao = new OrderItemDaoImpl();
    private BookDao bookDao = new BookDaoImpl();

    /**
     * 一定要先生成订单Order，在生成OrderItem因为OrderItem中需要订单号Order_Id作为外键
     * @param cart--->中含有以每个CartItem组成的Map，每个CartItem中包含了一本书的名称，价格price等信息，最重要还有一共买了几本这样的书的总价格totalPrice
     *            cart中的getTotalPrice()方法代表买许多本可能相同或不同的书的总共需要的钱，getTotalCount()购物车生成的订单一共有多少本书
     * @param userId
     * @return
     */
    @Override
    public String createOrder(Cart cart, Integer userId) {

        //订单号---需要是唯一的
        String orderId = System.currentTimeMillis()+""+userId;
        //创建一个订单对象
        Order order = new Order(orderId, new Date(), cart.getTotalPrice(), 0, userId);
        orderDao.saveOrder(order);

        //int i = 12 / 0;

        //遍历购物车Cart中的每一个商品项(即每一行，一行也表示一种书，但数量不确定)CartItem
        //cart.getItems().values();
        for (Map.Entry<Integer, CartItem> entry : cart.getItems().entrySet()){//cart.getItems().entrySet()--->Set<Map.Entry<K, V>>是一个Set集合，可以循环
            //获取购物车中的每一个商品项
            CartItem cartItem = entry.getValue();
            //转化为每一个订单项
            OrderItem orderItem = new OrderItem(null, cartItem.getName(), cartItem.getCount(), cartItem.getPrice(), cartItem.getTotalPrice(), orderId);
            //保存订单项到数据库
            orderItemDao.saveOrderItem(orderItem);//每个orderItem就是原来Cart购物车中的一行，因此要保存很多行，要生成多个订单项

            //每保存一个订单项，那么图书的库存和销量也要进行修改
            Book book = bookDao.queryBookById(cartItem.getId());
            book.setSales(book.getSales() + cartItem.getCount());
            book.setStock(book.getStock() - cartItem.getCount());
            bookDao.updateBook(book);

            //订单和订单项可以从双十一我们购物看出：
                //我们会把购物车中很多行商品一起结算，因此，很多商品项才构成一个订单，而订单的组成元素也很简单，只需要记录是哪个人的订单userId，和总共需要支付多少钱cart.getTotalPrice()
                //而订单项：我们虽然一起支付了，但是到最后在待发货那一项中：还是会每一个商品项为一行，也就形成了一行一个订单项
        }
        //清空购物车
        cart.clear();

        return orderId;
    }
}
