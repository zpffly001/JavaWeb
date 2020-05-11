package com.atguigu.utils;

import com.atguigu.pojo.User;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class WebUtils {

    public static <T> T copyParmToBean(Map value, T bean){
        try {
            //User user = new User();这里的User对象不需要创建，我们在子类中创建，这里放到方法的形参中，作为参数传入，在其他类调用时进来，提高了可移植性
            System.out.println("注入之前" + bean);
            //这一行代码就把所有的请求参数注入到了user类中，即少取了上面的第一步操作，和把上一步获取的参数一一放入有参构造器中，这些步骤
            BeanUtils.populate(bean, value);
            System.out.println("注入之后" + bean);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bean;

    }

    public static int parseInt(String id, int defaultValue){
        if (id != null){
            int i = 0;
            try {
                return i = Integer.parseInt(id);
            } catch (Exception e) {
                //e.printStackTrace();
            }
        }
//        int i = 0;
//        try {
//            return i = Integer.parseInt(id);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return defaultValue;
    }

}
