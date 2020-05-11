package com.atguigu.test;

import org.junit.Test;
import com.atguigu.utils.jdbcUtils;

public class JdbcUtilsTest {

    @Test
    public void testJdbcUtils(){
        System.out.println(jdbcUtils.getConnection());
    }

}
