package cn.topicstudy.jdbctemplate.connection.selectStrategy;

import cn.topicstudy.jdbctemplate.connection.ConnectionPool;

import java.sql.Connection;

public interface SelectStrategy {
    Connection select(ConnectionPool connectionPool) throws Exception;
}
