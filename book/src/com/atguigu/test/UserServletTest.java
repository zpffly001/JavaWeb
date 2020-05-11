package com.atguigu.test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class UserServletTest {

    public void login() {
        System.out.println("这是login()方法调用了");
    }

    public void regist() {
        System.out.println("这是regist()方法调用了");
    }

    public void updateUser() {
        System.out.println("这是updateUser()方法调用了");
    }

    public void updateUserPassword() {
        System.out.println("这是updateUserPassword()方法调用了");
    }

    public static void main(String[] args) {
        String action = "updateUserPassword";

        try {
            //通过反射，在UserServletTest类或者父类(因为加了Declared)得到对应名称的方法对象,如果加上method.setAccessible(true);就表明什么保险及别的都能调用包括private修饰的
            Method method = UserServletTest.class.getDeclaredMethod(action);

            //方法调用，要指明是该类的哪个对象的调用，因为一个类可以实例化多个对象
            method.invoke(new UserServletTest());//参数哪个属性可有可无，我们这里不需要参数，因此不写参数这个形参

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
