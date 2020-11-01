package com.wjh.config;

/**
 * JDBC相关配置
 */
public class JdbcConfig {
    //连接池中最大的连接数量
    public static Integer CONNECTION_MAX_COUNT = 2;
    //数据库
    public static String DB_URL = "jdbc:mysql://127.0.0.1:3306/wjhdb?useSSL=false&serverTimezone=GMT%2B8";
    public static String DB_USER = "root";
    public static String DB_PWD = "12345";
}
