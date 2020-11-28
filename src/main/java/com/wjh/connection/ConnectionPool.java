package com.wjh.connection;

import com.wjh.config.JdbcConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 连接池
 * 连接：jvm-database
 */
public class ConnectionPool {
    //连接池中最大的连接数量
    private static Integer maxCount;
    //当前连接池中的连接数量
    private static Integer currentCount = 0;
    //连接池
    private static List<Connection> connectionList = new ArrayList<Connection>();
    //connectionList中每个连接的创建时间
    //private static List<Date> connectionCreateTimeList = new ArrayList<Date>();

    /**
     * 从连接池中获取1个连接
     * 获取算法：随机
     */
    public static Connection getConnection() {
        if (currentCount <= 0) {
            try {
                throw new Exception("连接池中的连接数为0");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 随机取一个connection
        Connection connection = connectionList.get(new Random().nextInt(currentCount));
        try {
            // 当出现超时的connection就刷新连接池
            if(connection.isClosed()){
                refresh();
                connection = connectionList.get(new Random().nextInt(currentCount));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * 初始化连接池
     */
    public static void init() {
        try {
            //初始化连接池中最大的连接数量
            maxCount = JdbcConfig.CONNECTION_MAX_COUNT;
            //初始化连接池
            for (int i = 0; i < maxCount; i++) {
                Connection c = DriverManager.getConnection(JdbcConfig.DB_URL, JdbcConfig.DB_USER, JdbcConfig.DB_PWD);
                connectionList.add(c);
                currentCount++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 刷新连接池
     */
    private static void refresh(){
        init();
    }
}
