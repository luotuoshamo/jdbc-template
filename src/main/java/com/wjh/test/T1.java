package com.wjh.test;

import com.wjh.connection.ConnectionPool;

import java.sql.*;

public class T1 {
    public static void main(String[] args) throws Exception {
        String db_url = "jdbc:mysql://127.0.0.1:3306/wjhdb?useSSL=false";
        String db_user = "root";
        String db_pwd = "1234";
        //加载MySql驱动
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection(db_url, db_user, db_pwd);

        String sql = "select * from user";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        ConnectionPool.getConnection();
        while (resultSet.next()) {
            ResultSet row = resultSet;
            ResultSetMetaData metaData = row.getMetaData();
           int columnCount = metaData.getColumnCount();
            String columnLabel = metaData.getColumnLabel(1);
            String columnName = metaData.getColumnName(1);
            int columnType = metaData.getColumnType(1);
            String columnTypeName = metaData.getColumnTypeName(1);
           // System.out.println("columnCount=" + columnCount);
            System.out.println("columnLabel=" + columnLabel);
            System.out.println("columnName=" + columnName);
            System.out.println("columnType=" + columnType);
            System.out.println("columnTypeName=" + columnTypeName);
            System.out.println("v=" + row.getObject(columnLabel));

        }
    }
}
