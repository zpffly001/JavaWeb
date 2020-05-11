package com.atguigu.utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class jdbcUtils {

    private static DruidDataSource dataSource;
    private static ThreadLocal<Connection> conns = new ThreadLocal<>();

    static {
        try {
            Properties properties = new Properties();
            //读取jdbc.properties配置文件
            InputStream inputStream = jdbcUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
            //从流中加载数据
            properties.load(inputStream);
            //创建了数据库连接池
            dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//    Connection conn = null;
//
//        try {
//        conn = dataSource.getConnection();
//    } catch (SQLException throwables) {
//        throwables.printStackTrace();
//    }
//
//        return conn;之前的没有加上事务的获取连接的操作
    /**
     * 获取数据库连接池
     * @return如果返回null,说明获取连接失败<br/>有值就是获取连接成功
     */
    public static Connection getConnection(){
        Connection conn = conns.get();//由于先前我们没有向conns中放入连接，因此下面需要判断
        if (conn == null){
            try {
                conn = dataSource.getConnection();//第一次conns.get()没有东西，因此我们要从数据库连接池中获取连接
                conns.set(conn);//当前数据库连接对象/线程保存到ThreadLocal对象中，供后面的JDBC操作使用
                conn.setAutoCommit(false);//设置为手动管理事务
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return conn;//一次事务操作，可能要有多次连接数据库操作，因此就会多次调用此方法，第一次调用时，conns.get()得不到连接，因为我们还没有放入连接，第二次及后面，得到的conn都是第一次的conn,放入conns然后我们get()出来的
    }

    /**
     * 提交事务，并关闭释放连接，这两个操作就可以一起做了
     */
    public static void commitAndClose(){
        Connection connection = conns.get();
        if (connection != null){//如果不等于null，说明之前使用过连接，操作过数据库

            try {
                connection.commit();//提交事务
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }finally {
                try {
                    connection.close();//关闭连接释放资源
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        //一定要执行remove操作，否则就会出错(因为Tomcat服务器，底层使用了线程池技术)
        conns.remove();
    }

    public static void rollbackAndClose(){
        Connection connection = conns.get();
        if (connection != null){//如果不等于null，说明之前使用过连接，操作过数据库

            try {
                connection.rollback();//事务回滚
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }finally {
                try {
                    connection.close();//关闭连接释放资源
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        //一定要执行remove操作，否则就会出错(因为Tomcat服务器，底层使用了线程池技术)
        conns.remove();
    }


    /**
     * 关闭连接，放回数据库连接池
     * @param conn
     */
//    public static void close(Connection conn){
//        if (conn != null) {
//            try {
//                conn.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }

}
