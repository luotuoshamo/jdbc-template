package com.wjh.util;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StatementUtil {
    /**
     * 关闭statement 为了少些try catch
     */
    public static void close(PreparedStatement statement) {
        if (statement == null) {
            return;
        }
        try {
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
