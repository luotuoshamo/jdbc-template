## 概述
* 轻量级封装jdbc
* 调用时的入口只有一个类：JdbcUtil

## 设置数据库信息（必须要设置）
* API
```java
JdbcUtil.initJdbcConfig(String db_url, String db_user, String db_pwd)
JdbcUtil.initJdbcConfig(String db_url, String db_user, String db_pwd, int connectionMaxCount)
```
* 例子
SpringBoot项目这样设置
```java
@SpringBootApplication
public class App {
    public static String DB_URL = "jdbc:mysql://127.0.0.1:3306/wjhdb?useSSL=false&serverTimezone=GMT%2B8";
    public static String DB_USER = "root";
    public static String DB_PWD = "1234x";

    public static void main(String[] args) {
        //设置数据库信息
        JdbcUtil.initJdbcConfig(DB_URL, DB_USER, DB_PWD);
       
        SpringApplication.run(App.class);
    }
}
```
普通项目这样设置
```java
public class testT1 {
    public static String DB_URL = "jdbc:mysql://127.0.0.1:3306/wjhdb?useSSL=false&serverTimezone=GMT%2B8";
    public static String DB_USER = "root";
    public static String DB_PWD = "1234";

    public static void main(String[] args) {
        JdbcUtil.initJdbcConfig(DB_URL, DB_USER, DB_PWD, 4);
        List<Map<String, Object>> query = JdbcUtil.query("select * from user");
        System.out.println(query);
    }
}
```

## 查询
<p>给sql传参数可以直接写，也可以用数组</p>
* API
```java
JdbcUtil.query(String sql, Object... params)
```
* 例子
```java
Object[] a = {2};
List<Map<String, Object>> list = JdbcUtil.query("select * from user a where a.id>? ", a);
或
List<Map<String, Object>> list = JdbcUtil.query("select * from user a where a.id>? ", 2);
```

## 增删改
* API
```java
JdbcUtil.update(String sql, Object... params) 无事务
update(String sql, Connection connection, Object... params) 有事务
```
* 例子
无事务
```java
Integer i = JdbcUtil.update("update user set name=? where id=?", "newName", 2);

Object[] a = {"newName", 2};
Integer i = JdbcUtil.update("update user set name=? where id=?", a);
```
有事务
```java
Connection connection = JdbcUtil.beginTransaction();

Object[] a = {"newName", 2};
JdbcUtil.update("update user set name=? where id=?", connection, a);
int x = 1 / 0;

JdbcUtil.commitTransaction(connection);
```

