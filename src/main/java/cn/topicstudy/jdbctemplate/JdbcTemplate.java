package cn.topicstudy.jdbctemplate;

import cn.topicstudy.jdbctemplate.connection.ConnectionPool;
import cn.topicstudy.jdbctemplate.connection.DataSource;
import cn.topicstudy.jdbctemplate.connection.selectStrategy.RandomSelectStrategy;
import cn.topicstudy.jdbctemplate.connection.selectStrategy.SelectStrategy;
import cn.topicstudy.jdbctemplate.util.DataBaseTypeEnum;
import cn.topicstudy.jdbctemplate.util.ResultSetUtil;
import cn.topicstudy.jdbctemplate.util.UrlUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

/**
 * 入口
 */
public class JdbcTemplate {
    private ConnectionPool connectionPool;

    public JdbcTemplate(DataSource dataSource) {
        this(dataSource, new RandomSelectStrategy());
    }

    public JdbcTemplate(DataSource dataSource, SelectStrategy selectStrategy) {
        this.connectionPool = new ConnectionPool(dataSource, selectStrategy);
    }


    /**
     * 读（查询）
     * 不抛出异常，如果查询失败则返回空list（空list不是null）
     *
     * @param sql 如 select name myName，company from user a where a.id > ?
     */
    public List<Map<String, Object>> query(String sql, Object... params) throws Exception {
        Connection connection = connectionPool.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql);
        // 设置参数到sql中
        for (int i = 0; i < params.length; i++) {
            ps.setObject(i + 1, params[i]);
        }
        ResultSet resultSet = ps.executeQuery();
        return ResultSetUtil.resultSetToList(resultSet);
    }

    /**
     * 写（增删改）
     */
    public int update(String sql, Object... params) throws Exception {
        return update(sql, connectionPool.getConnection(), params);
    }

    /**
     * 批量写
     * MySQL批处理时必需设置参数rewriteBatchedStatements，且参数中字母的大小写必需这样
     */
    public int[] updateBatch(String sql, List<Object[]> paramsList) throws Exception {
        if (!isAllowBatch()) throw new Exception("未开启批处理，MySQL设置rewriteBatchedStatements=true");

        Connection connection = connectionPool.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql);
        for (Object[] params : paramsList) {
            // 给1条sql设置参数
            for (int i = 0; i < params.length; i++)
                ps.setObject(i + 1, params[i]);
            ps.addBatch();
        }
        return ps.executeBatch();
    }

    /**
     * 写（增删改）
     *
     * @param connection 用于实现事务
     */
    public Integer update(String sql, Connection connection, Object... params) throws Exception {
        PreparedStatement ps = connection.prepareStatement(sql);
        // 设置参数到sql中
        for (int i = 0; i < params.length; i++) {
            ps.setObject(i + 1, params[i]);
        }
        return ps.executeUpdate();
    }

    /**
     * 判断当前数据库是否开启了批处理
     */
    private boolean isAllowBatch() {
        DataSource ds = connectionPool.getDataSource();
        DataBaseTypeEnum databaseType = ds.getDatabaseType();
        String url = ds.getUrl();
        Map<String, String> queryMap = UrlUtil.getQueryMapFromUrl(url);
        String allowBatchKey = "rewriteBatchedStatements";
        switch (databaseType) {
            case MYSQL:
                if (!queryMap.containsKey(allowBatchKey)
                        || !"true".equals(queryMap.get(allowBatchKey))) {
                    return false;
                }
                break;
            case ORACLE:
                return true;
        }
        return true;
    }
}
