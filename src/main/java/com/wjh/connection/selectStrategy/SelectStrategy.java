package com.wjh.connection.selectStrategy;

import com.wjh.connection.ConnectionPool;

import java.sql.Connection;

public interface SelectStrategy {
    Connection select(ConnectionPool connectionPool) throws Exception;
}
