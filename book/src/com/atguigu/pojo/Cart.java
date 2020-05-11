package com.atguigu.pojo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/*购物车对象
 */
public class Cart {

    //private Integer totalCount;
    //private BigDecimal totalPrice;
    /**
     * key是商品编号，value是商品信息
     */
    private Map<Integer, CartItem> items = new LinkedHashMap<>();

    /**
     * 添加商品
     * @param cartItem
     */
    public void addItem(CartItem cartItem){
        //先查看购物车中是否已经添加过此商品，如果已添加，则数量累加，总金额更新，如果没有添加过，直接放入List集合，然后遍历输出
        CartItem item = items.get(cartItem.getId());

        if (item == null){
            //之前没有添加过此商品
            items.put(cartItem.getId(), cartItem);
        }else {
            //已经添加过
            item.setCount(item.getCount() + 1);//数量累加
            item.setTotalPrice(item.getPrice().multiply(new BigDecimal(item.getCount())));//multiply是乘法方法，参数必须是BigDecimal类型，即商品数量乘以单价得到该类商品总价
        }
    }

    /**
     * 删除商品项
     * @param id
     */
    public void deleteItem(Integer id){
        items.remove(id);
    }

    /**
     * 清空购物车
     */
    public void clear(){
        items.clear();
    }

    /**
     * 修改商品数量
     * @return
     */
    public void updateCount(Integer id, Integer count){
        // 先查看购物车中是否有此商品。如果有，修改商品数量，更新总金额
        CartItem cartItem = items.get(id);
        if (cartItem != null) {
            cartItem.setCount(count);// 修改商品数量
            cartItem.setTotalPrice( cartItem.getPrice().multiply(new BigDecimal( cartItem.getCount() )) ); // 更新总金额
        }
    }

    public Integer getTotalCount() {
        Integer totalCount = 0;

        //方法一：利用entrySet()遍历，因为此Map类型的实现类为LinkenHashMap是链表结构，知道相邻的cartItem
        for (Map.Entry<Integer, CartItem> entry : items.entrySet()){
            totalCount += entry.getValue().getCount();
        }

        //方法二：item.values()得到每个商品项CartItem，Collection<CartItem>然后再遍历
        //for (CartItem value : items.values()){}

        return totalCount;
    }



    public BigDecimal getTotalPrice() {

        BigDecimal totalPrice = new BigDecimal(0);

        for (Map.Entry<Integer, CartItem> entry : items.entrySet()){
            totalPrice = totalPrice.add(entry.getValue().getTotalPrice());//BigDecimal类型的相加，必须使用.add()方法而不是 +
        }

        return totalPrice;
    }



    public Map<Integer, CartItem> getItems() {
        return items;
    }

    public void setItems(Map<Integer, CartItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "totalCount=" + getTotalCount() +
                ", totalPrice=" + getTotalPrice() +
                ", items=" + items +
                '}';
    }
}
