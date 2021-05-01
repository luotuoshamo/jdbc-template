## 概述
* 轻量级封装JDBC

* 基于JDK1.8和MySQL5.1.48测试的

  #  API

  * 配置数据源

    ```java
    private DataSource dataSource() {
    	DataSource dataSource = new DataSource();
    	dataSource.setDatabaseType(DataBaseTypeEnum.MYSQL);
    	dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/wjhdb?useSSL=false&serverTimezone=GMT%2B8");
    	dataSource.setUser("root");
    	dataSource.setPwd("123456");
    	dataSource.setMinConnectionCount(2);
    	dataSource.setMaxConnectionCount(10);
    	return dataSource;
    }
    ```

    

  * 读

    `jdbcTemplate.query`

    ```java
    @Test
    public void query() throws Exception {
    	DataSource dataSource = dataSource();
    	JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    	String sql = "  select id uId, name from user a where id > ? and name <> ?  ";
    	Object[] params = {1, "张三"};
    	List<Map<String, Object>> list = jdbcTemplate.query(sql, params);
    	System.out.println(list);
    }   
    ```

    

  * 写

    `jdbcTemplate.update`

    ```java
    @Test
    public void update() throws Exception {
    	DataSource dataSource = dataSource();
    	JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    	String sql = "  update user a set a.name = ? where a.id = ?  ";
    	Object[] params = {"张三", 2};
    	int i = jdbcTemplate.update(sql, params);
    	System.out.println("写结果=" + i);
    }
    
    ```

    



## 
