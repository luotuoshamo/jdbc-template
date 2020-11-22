package com.wjh;

import com.wjh.config.JdbcConfig;
import com.wjh.connection.ConnectionPool;
import com.wjh.util.ResultSetUtil;
import com.wjh.util.StatementUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JdbcUtil {
    /**
     * 初始化数据源（数据库信息，因为数据库是数据源头，所以叫数据源）
     */
    public static void initJdbcConfig(String db_url, String db_user, String db_pwd) {
        JdbcConfig.DB_URL = db_url;
        JdbcConfig.DB_USER = db_user;
        JdbcConfig.DB_PWD = db_url;
    }

    /**
     * 初始化数据源（数据库信息，因为数据库是数据源头，所以叫数据源）
     */
    public static void initJdbcConfig(String db_url, String db_user, String db_pwd, int connectionMaxCount) {
        JdbcConfig.DB_URL = db_url;
        JdbcConfig.DB_USER = db_user;
        JdbcConfig.DB_PWD = db_pwd;

        JdbcConfig.CONNECTION_MAX_COUNT = connectionMaxCount;
        ConnectionPool.init();
    }

    /**
     * 从连接池获取connection
     */
    public static Connection getConnection() {
        return ConnectionPool.getConnection();
    }

    /**
     * 开启事务
     */
    public static Connection beginTransaction() {
        Connection connection = getConnection();
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * 提交事务
     */
    public static void commitTransaction(Connection connection) {
        try {
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 回滚事务
     */
    public static void rollbackTransaction(Connection connection) {
        try {
            connection.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读（即 查询）
     *
     * @param sql select * from user a where a.id>?
     */
    public static List<Map<String, Object>> query(String sql, Object... params) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            //设置参数到sql中
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
            resultSet = statement.executeQuery();
            list = ResultSetUtil.resultSetToList(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭资源 不关闭connection 因为使用连接池
            StatementUtil.close(statement);
            ResultSetUtil.close(resultSet);
        }
        return list;
    }

    /**
     * 写（即 增删改）
     */
    public static Integer update(String sql, Object... params) {
        Connection connection = getConnection();
        return update(sql, connection, params);
    }

    /**
     * 增加参数connection为了使用事务
     * 同一个connection时事务才有用
     */
    public static Integer update(String sql, Connection connection, Object... params) {
        int updateRowCount = 0;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            //设置参数到sql中
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
            updateRowCount = statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭资源 不关闭connection 因为使用连接池
            StatementUtil.close(statement);
        }
        return updateRowCount;
    }
}
