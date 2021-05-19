# 概述
* 轻量级封装JDBC
* 基于JDK1.8和MySQL5.1.48测试的

#  API及示例

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

    `List<Map<String, Object>> query(String sql, Object... params)`

    ```java
    @Test
    public void query() throws Exception {
    	DataSource dataSource = dataSource();
    	JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    	String sql = "  select id uId, name from user a where id > ? and name <> ?  ";
    	Object[] params = {1, "张三"};
    	List<Map<String, Object>> list = jdbcTemplate.query(sql, params);
    	System.out.println("读结果=" + list);
    }   
    ```
  * 写
    `int update(String sql, Object... params)`
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
    * 批处理
      `int[] updateBatch(String sql, List<Object[]> paramsList)`
    
      参数List<Object[]>中的每个Object[]表示1个sql的参数
```java
	@Test
	public void saveOnceBatch() throws Exception {
	    JdbcTemplate jt = jdbcTemplate();
	    String sql = "insert into emp(center,dept,name,state) values(?,?,?,?)";
	    List<Object[]> paramsList = new ArrayList<Object[]>();
	    for (Map<String, String> mapRow : readExcel()) {
	        String[] params = { mapRow.get("上级机构"), 
	                            mapRow.get("部门名称"),          
	                            mapRow.get("姓名"),
                            mapRow.get("在职状态")
                              };
            paramsList.add(params);
        }
        jt.updateBatch(sql, paramsList);
    }
    ```

