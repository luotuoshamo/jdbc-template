package com.wjh.test;

import com.wjh.JdbcUtil;
import com.wjh.connection.ConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class T2 {
    public static void main(String[] args) throws SQLException {
        /*Object[] a = {2};
        List<Map<String, Object>> list = JdbcUtil.query("select * from user a where a.id>? ", a);
        System.out.println(list);  */
     /*   List<Map<String, Object>> list = JdbcUtil.query("select * from user a where a.id>? ", 2);
        System.out.println(list);*/


        //Integer i = JdbcUtil.update("update user set name=? where id =?", "newName", 2);

       /* Object[] a = {"newName", 2};
        Integer i = JdbcUtil.update("update user set name=? where id =?", a);*/

        Connection connection = JdbcUtil.beginTransaction();

        Object[] a = {"newName2-3", 2};
        JdbcUtil.update("update user set name=? where id =?",
                connection, a);
        int x = 1 / 0;

        JdbcUtil.commitTransaction(connection);
    }
}
