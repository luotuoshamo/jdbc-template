package cn.topicstudy.jdbctemplate.util;

import cn.topicstudy.jdbctemplate.connection.DataSource;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionUtil {
    /**
     * 判断connection是否有效（无效：超时...）
     */
    public static boolean isEffectiveConnection(DataBaseTypeEnum dataBaseTypeEnum, Connection connection) throws Exception {
        String testSql;
        switch (dataBaseTypeEnum) {
            case MYSQL:
                testSql = "select 1";
                break;
            case ORACLE:
                testSql = "select 1 from dual";
                break;
            default:
                throw new Exception("未配置数据源的DATABASE_TYPE");
        }

        // 测试connection
        try {
            connection.prepareStatement(testSql).executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 创建connection
     */
    public static Connection creatConnection(DataSource dataSource) throws Exception {
        return DriverManager.getConnection(
                dataSource.getUrl(),
                dataSource.getUser(),
                dataSource.getPwd()
        );
    }
}
