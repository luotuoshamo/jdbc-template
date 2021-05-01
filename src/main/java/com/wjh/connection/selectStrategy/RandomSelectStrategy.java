package com.wjh.connection.selectStrategy;

import com.wjh.connection.ConnectionPool;

import java.sql.Connection;
import java.util.List;
import java.util.Random;

public class RandomSelectStrategy implements SelectStrategy {
    public Connection select(ConnectionPool connectionPool) throws Exception {
        if (connectionPool.isEmpty()) throw new Exception("连接池为空");
        List<Connection> connectionList = connectionPool.getConnectionList();
        int connectionCount = connectionList.size();
        int randomIndex = new Random().nextInt(connectionCount);
        return connectionList.get(randomIndex);
    }
}
