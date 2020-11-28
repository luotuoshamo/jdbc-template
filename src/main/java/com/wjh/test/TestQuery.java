package com.wjh.test;

import com.wjh.JdbcUtil;

import java.util.List;
import java.util.Map;

public class TestQuery {
    //连接池中最大的连接数量
    public static Integer CONNECTION_MAX_COUNT = 2;
    //数据库
    public static String DB_URL = "jdbc:mysql://127.0.0.1:3306/wjhdb?useSSL=false&serverTimezone=GMT%2B8";
    public static String DB_USER = "root";
    public static String DB_PWD = "1234";

    public static void main(String[] args) {

        JdbcUtil.initJdbcConfig(DB_URL, DB_USER, DB_PWD,9);
        Object[] a = {2};
        List<Map<String, Object>> list = JdbcUtil.query("select * from user a where a.id>? ", a);
        System.out.println(list);
    }
}
