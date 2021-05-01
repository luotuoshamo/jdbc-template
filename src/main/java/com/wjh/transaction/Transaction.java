package com.wjh.transaction;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 同一个connection时事务才有用
 */
public class Transaction {
    public static Connection connection;

    public Transaction(Connection connection) {
        this.connection = connection;
    }

    /**
     * 开启事务
     */
    public void begin() {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 提交事务
     */
    public void commit(Connection connection) {
        try {
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 回滚事务
     */
    public static void rollback(Connection connection) {
        try {
            connection.rollback();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
