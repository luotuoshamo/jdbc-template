package com.wjh.connection;

import com.wjh.connection.selectStrategy.SelectStrategy;
import com.wjh.util.ConnectionUtil;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * 连接池
 */
public class ConnectionPool {
    // 数据源
    private DataSource dataSource;
    // 从连接池中获取连接的策略
    private SelectStrategy selectStrategy;
    // 连接池
    private List<Connection> connectionList = new ArrayList<Connection>();

    public ConnectionPool(DataSource dataSource, SelectStrategy selectStrategy) {
        try {
            dataSource.check();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.dataSource = dataSource;
        this.selectStrategy = selectStrategy;

        //初始化连接池
        for (int i = 0; i < dataSource.getMaxConnectionCount(); i++) {
            try {
                connectionList.add(ConnectionUtil.creatConnection(dataSource));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public List<Connection> getConnectionList() {
        return connectionList;
    }

    /**
     * 从连接池中获取连接
     * 对取出的connection测试下，如果失效，则再取一次，直到取到有效的的连接
     */
    public Connection getConnection() throws Exception {
        while (true) {
            checkConnectionCount();
            // 取1个connection
            Connection connection = selectStrategy.select(this);
            if (ConnectionUtil.isEffectiveConnection(dataSource.getDatabaseType(), connection)) return connection;
            connectionList.remove(connection);
        }
    }

    /**
     * 判断连接池中连接数量是否足够
     */
    private void checkConnectionCount() throws Exception {
        if (connectionList.size() < dataSource.getMinConnectionCount()) {
            // 补充连接池中连接的数量
            int count = dataSource.getMaxConnectionCount() - connectionList.size();
            for (int i = 0; i < count; i++) {
                connectionList.add(ConnectionUtil.creatConnection(dataSource));
            }
        }
    }

    public boolean isEmpty() {
        return this == null || connectionList == null || connectionList.size() == 0;
    }
}
