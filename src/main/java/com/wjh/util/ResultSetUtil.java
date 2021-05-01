package com.wjh.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 操作java.sql.ResultSet的工具类
 */
public class ResultSetUtil {
    /**
     * 将java.sql.ResultSet转成java.util.List
     */
    public static List<Map<String, Object>> resultSetToList(ResultSet resultSet) throws Exception {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        while (resultSet.next()) {
            Map<String, Object> map = new HashMap<String, Object>();
            ResultSet row = resultSet;// 1行数据
            ResultSetMetaData metaData = row.getMetaData();
            int columnCount = metaData.getColumnCount();
            // 将1行数据组装成Map
            for (int i = 0; i < columnCount; i++) {
                // select id myId from user columnName是id，columnLabel是myId
                // String columnName = metaData.getColumnName(i + 1);
                String columnLabel = metaData.getColumnLabel(i + 1);
                Object columnValue = row.getObject(i + 1);
                map.put(columnLabel, columnValue);
            }
            list.add(map);
        }
        return list;
    }

    /**
     * 关闭resultSet 为了少些try catch
     */
    public static void close(ResultSet resultSet) {
        if (resultSet == null) return;

        try {
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
